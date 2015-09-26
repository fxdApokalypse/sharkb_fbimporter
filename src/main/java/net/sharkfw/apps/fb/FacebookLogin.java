package net.sharkfw.apps.fb;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.sharkfw.apps.fb.conf.AppConfig;
import net.sharkfw.apps.fb.conf.ConfigurationException;
import net.sharkfw.apps.fb.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FacebookLogin extends Application {

    public static final String SCOPE_DELIMITER = ",";

    public static class FacebookLoginResponse {
        private String accessToken;
        private String error;
        private String errorReason;
        private String errorDescription;

        public FacebookLoginResponse(String loginResponseFragment) {
            if (loginResponseFragment == null) {
                this.accessToken = "";
                LOG.error("Login failed fall back to the configured access token");
            }

            Map<String, String> params = StringUtils.parseFragment(loginResponseFragment);

            this.accessToken = params.get(PARAM_ACCESS_TOKEN);
            this.error = params.get(PARAM_ERROR);
            this.errorReason = params.get(PARAM_ERROR_REASON);
            this.errorDescription = params.get(PARAM_ERROR_DESCRIPTION);
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getError() {
            return error;
        }

        public String getErrorReason() {
            return errorReason;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public boolean isFailed() {
            return getError() != null;
        }

        @Override
        public String toString() {
            if (isFailed()) {
                return "LoginError: " + getError() + " cause of " + getErrorReason() + " - " + getErrorDescription();
            }
            return getAccessToken();
        }

    }

    /**
     * Class Logger
     */
    private final static Logger LOG = LoggerFactory.getLogger(FacebookLogin.class);


    private static final String FB_LOGIN_URL = "https://www.facebook.com/dialog/oauth";
    private static final String REDIRECT_URL = "https://www.facebook.com/connect/login_success.html";

    public static final String PARAM_ACCESS_TOKEN = "access_token";
    public static final String PARAM_ERROR = "error";
    public static final String PARAM_SCOPE = "scope";
    public static final String PARAM_ERROR_REASON = "error_reason";
    public static final String PARAM_ERROR_DESCRIPTION = "error_description";

    public static final String ARG_REQUIRED_SCOPE_SHORT = "-s";
    public static final String ARG_REQUIRED_SCOPE = "--scope";
    public static final String ARG_APP_ID_SHORT= "-a";
    public static final String ARG_APP_ID= "--app-id";
    public static final String ARG_SAVE_APP_ID= "--save";
    public static final String ARG_HELP_SHORT= "-h";
    public static final String ARG_HELP= "--help";

    /**
     * The Application context of this application.
     */
    private AnnotationConfigApplicationContext ctx;

    /**
     * A set of permissions which should be requested
     * from the user.
     */
    private  Set<String> requiredPermissions;

    /**
     * The app id of the underlying facebook app.
     */
    private String facebookAppId;

    /**
     * If true the new obtained access token is saved
     * in the file conf/conf.properties
     */
    private boolean saveNewAccessToken = false;


    @Override
    public void start(Stage primaryStage) throws Exception {


        requiredPermissions = new HashSet<>();
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.registerShutdownHook();
        ctx.refresh();

        facebookAppId = ctx.getEnvironment().getProperty(AppConfig.FACEBOOK_APP_ID);
        parseArgs();

        if (facebookAppId == null) {
            throw new ConfigurationException("Missing the required " + AppConfig.FACEBOOK_APP_ID );
        }

        URI loginURI = buildFBLoginURL();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.locationProperty().addListener((ov, oldLocation, newLocation) -> {
            if (newLocation.startsWith(REDIRECT_URL)) {
                URI loginResponseURL = URI.create(newLocation);
                String loginResponseFragment = loginResponseURL.getFragment();

                if (saveNewAccessToken) {
                    saveAccessToken(loginResponseFragment);
                }

                System.out.println(loginResponseFragment);
                primaryStage.close();
            }
        });

        webEngine.setJavaScriptEnabled(true);
        webEngine.load(loginURI.toString());

        // Szene
        Scene scene = new Scene(webView);
        primaryStage.setTitle("Facebook Importer Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveAccessToken(String loginResponseFragment) {
        FacebookLoginResponse response = new FacebookLoginResponse(loginResponseFragment);
        if (! response.isFailed()) {
            String[] configSources = AppConfig.class.getAnnotation(PropertySource.class).value();
            PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(ctx.getEnvironment().getPropertySources());
            String configFile = null;
            try {
                URI configURI = URI.create(resolver.resolvePlaceholders(configSources[0]));
                configFile = configURI.toURL().getFile();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String oldContent = null;
            try (InputStream in = new FileInputStream(configFile)) {
                oldContent =  StreamUtils.copyToString(in, Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter out = new FileWriter(configFile)) {
                for (String line : oldContent.split(StringUtils.NEWLINE)) {
                    if (line.startsWith(AppConfig.FACEBOOK_ACCESS_TOKEN_PROPERTY)) {
                        out.write(AppConfig.FACEBOOK_ACCESS_TOKEN_PROPERTY + "=" + response.getAccessToken());
                    } else {
                        out.write(line);
                        out.write(StringUtils.NEWLINE);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseArgs() {

        Runnable printHelp = () -> {
            StringBuilder str = new StringBuilder();
            str.append("Purpose: Obtains a access token from facebook user for a specified facebook app.").append(StringUtils.NEWLINE);
            str.append("Usage: login [OPTION...]").append(StringUtils.NEWLINE);
            str.append(StringUtils.NEWLINE);
            String
                scopeParam = String.join(", ", ARG_REQUIRED_SCOPE_SHORT, ARG_REQUIRED_SCOPE );
                scopeParam += " PERMISSION"+ SCOPE_DELIMITER +"...";
            str.append(String.format("  %-30s request this permission from the facebook user,", scopeParam)).append(StringUtils.NEWLINE);

            String
                appIdParam = String.join(", ", ARG_APP_ID_SHORT, ARG_APP_ID );
                appIdParam += " APPID";
            str.append(String.format("  %-30s use this app id for the facebook login,", appIdParam )).append(StringUtils.NEWLINE);
            str.append(String.format("  %-30s if omitted  the appId is read from", "")).append(StringUtils.NEWLINE);
            str.append(String.format("  %-30s the conf/conf.properties file.","")).append(StringUtils.NEWLINE);
            str.append(StringUtils.NEWLINE);

            String
                saveAccessToken = String.join(", ", ARG_SAVE_APP_ID );
            str.append(String.format("  %-30s saves the access token in conf/conf.properties.", saveAccessToken )).append(StringUtils.NEWLINE);

            String
                showHelpParam = String.join(", ", ARG_HELP_SHORT, ARG_HELP );
            str.append(String.format("  %-30s show the help.", showHelpParam )).append(StringUtils.NEWLINE);

            str.append(StringUtils.NEWLINE);
            str.append("Report bugs to https://github.com/yveskaufmann/sharkb_fbimporter.").append(StringUtils.NEWLINE);

            System.out.println(str.toString());
        };

        if (LOG.isDebugEnabled()) {
            LOG.debug("parse argument arguments");
            LOG.debug("ARGS: " + String.join(" ",getParameters().getRaw()));
        }

        List<String> args =  getParameters().getRaw();
        for (int i = 0; i < args.size(); i++) {

            switch (args.get(i)) {
                case ARG_REQUIRED_SCOPE_SHORT:
                case ARG_REQUIRED_SCOPE:
                    if (i + 1 < args.size() ) {
                        List<String> requiredPermissions = Arrays.asList(args.get(i + 1).split(SCOPE_DELIMITER));
                        this.requiredPermissions.addAll(requiredPermissions);
                    }
                    break;
                case ARG_APP_ID_SHORT:
                case ARG_APP_ID:
                    if (i + 1 < args.size() ) {
                       this.facebookAppId = args.get(i + 1);
                    }
                    break;
                case ARG_SAVE_APP_ID:
                    saveNewAccessToken = true;
            }
        }

        if (LOG.isDebugEnabled()) {
            this.requiredPermissions.forEach((scope) -> {
                LOG.debug("requested scope: " + scope);
            });

            LOG.debug("FacebookAppID = " + this.facebookAppId);
            LOG.debug("saveNewAccessToken = " + this.saveNewAccessToken);
        }

        if ( this.facebookAppId == null || this.facebookAppId == "" ) {
            System.err.println("Error: No AppId provided which is required for the login.");
            printHelp.run();
            System.exit(0);
        }
    }

    /**
     * Build the url to the facebook login form.
     *
     * @return the login url.
     */
    private URI buildFBLoginURL() {

        List<String> loginParams = new ArrayList<>();
        loginParams.add("client_id=" + facebookAppId);
        loginParams.add("redirect_uri=" + REDIRECT_URL);
        loginParams.add("response_type=token");
        if (requiredPermissions != null) {
            String scope = String.join(",", requiredPermissions);
            loginParams.add(PARAM_SCOPE + "=" +scope);
        }

        return URI.create(FB_LOGIN_URL + "?" + String.join("&", loginParams));
    }

    public static FacebookLoginResponse invokeLogin(String ...scope) {
        LOG.info("Start facebook login dialog");
        String loginResponse = null;
        URL[] classpathURLs =((URLClassLoader) (Thread.currentThread().getContextClassLoader())).getURLs();
        String classpath  = Stream.of(classpathURLs).map(Object::toString).collect(Collectors.joining(":"));
        String command = "java -cp " + classpath + " " + FacebookLogin.class.getName();

        if (scope.length > 0) {
            command += " --scope " + String.join(SCOPE_DELIMITER, scope);
        }

        try {
            Process process = Runtime.getRuntime().exec(command);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                loginResponse = reader.readLine();
            }
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        FacebookLoginResponse flp = new FacebookLoginResponse(loginResponse);
        return flp;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
