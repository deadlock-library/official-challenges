var Validator = require('jsonschema').Validator;
var v = new Validator();

var sampleSchema = {
  "id": "/Preset",
  "type": "object",
  "properties": {
    "name": {"type": "string", "required": true},
    "description": {"type": "string", "required": true},
    "keywords": {"type": "array", "items": {"type": "string"}, "required": true},
    "author": {"type": "string", "required": true},
    "revision": {"type": "number", "required": true}
  }
};

module.exports = {
  "validate": function (element) {
    return v.validate(element, sampleSchema);
  }
};
