[].slice.call(
	$$('h3#readfields + div > table:first-child > tbody tr')
).map(function(row) {
	var entry = {
		"name": row.children[0].innerText.trim(),
		"desc": row.children[1].innerText.trim(),
		"type": row.children[2].innerText.trim()
	}; 
	return entry;
}).map(function(entry) {
	return entry['name'] + ":" + entry['type'] + " - " + entry['desc'];
});