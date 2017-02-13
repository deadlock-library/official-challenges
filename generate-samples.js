// Imports
const fs = require('fs');
const path = require('path');
const yaml = require('js-yaml');
const rmdir = require('rmdir-recursive').sync;
const validator = require('./lib/schema');

// Constants
const outputDir = 'out/';
const baseDir = '../';

/**
 * Process yaml file and translate into JSON. Output in output/ directory.
 * @param f the input yaml file
 * @return {"name": {"en": "The Preset Name"}, "description": {"en": "Your preset description"}, "file": "preset_file.json", "image": "image_url"}
 */
var processYaml = function (f) {
  // Convert Yaml to JSON
  var parsed = yaml.safeLoad(fs.readFileSync(f));
  var validation = validator.validate(parsed);
  if (!validation.valid) {
    console.error("File " + f + " is invalid. Will be ignored in parsing.");
    console.error(validation.errors);
  } else {
    console.log("File " + f + " is valid. Will be parsed as preset.");
  }
  return {"name": parsed.name, "description": parsed.description, "revision": parsed.revision, "author": parsed.author, "keywords": parsed.keywords};
};

/**
 * Main processing function factory
 * @param presets the presets object
 * @returns {Function} the function which will process all files
 */
var getSampleProcessor = function(rootPath, samples) {
  return function (f) {
    try {
      console.log('Found sample ' + f);
      // Only process files
      var s = fs.statSync(rootPath + f);
      if (!s.isFile()) {
        return;
      }
      var filename = path.basename(rootPath);
      // Process yaml or json files
      switch (f) {
        case 'sample.yml':
        case 'sample.yaml':
          console.log('Found sample ' + filename);
          var p = processYaml(rootPath + f);
          samples[filename] = p;
          break;
      }
    } catch (err) {
      console.error(err);
    }
  }
};

var exec = function() {
  // Create output directory
  if (!fs.existsSync(outputDir)) {
    fs.mkdirSync(outputDir);
  }

  var dir = fs.readdirSync(baseDir);
  var output = {
    revision: process.argv[2],
    lastUpdate: new Date().toISOString(),
    samples: {}
  };

  /**
   * Parse directory
   */
  dir.forEach(function(d) {
    var rootPath = baseDir + d;
    var s = fs.statSync(rootPath);
    if (s.isFile()) {
      return;
    }
    var sampleDir = fs.readdirSync(rootPath);
    sampleDir.forEach(getSampleProcessor(rootPath + "/", output.samples));
  });
  fs.writeFileSync(outputDir + 'samples.json', JSON.stringify(output));

};


// Execute code
exec();
