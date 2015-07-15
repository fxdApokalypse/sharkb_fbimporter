package net.sharkfw.apps.fb.util.facebook.json.mixin;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class FacebookObjectMixin {

    @JsonAnySetter
    abstract void add(String key, Object value);

}