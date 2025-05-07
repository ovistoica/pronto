## This library follows [Semantic Versioning](https://semver.org).
## This CHANGELOG follows [keepachangelog](https://keepachangelog.com/en/1.0.0/).

###  VERSION [4.0.0]:
#### Breaking Changes
* Dropped support for Protobuf 3.x, now requires Protobuf 4.x or newer
* Replaced `GeneratedMessageV3` with `Message` in Java interfaces
* Updated all API calls to align with Protobuf 4.x conventions

#### Added
* Full support for Protobuf 4.x (tested with version 4.30.2)
* Better handling of optional fields in Protobuf 4.x
* Updated CI workflow to use Ubuntu 22.04 (from 20.04 which is retiring)

###  VERSION [3.0.0]:
#### Changed
* Minimal supported protobuf version 3.15.0.
* when a getting an optional field that was not set, a nil will be returned instead of the default value of this field.
* Allow assoc a nil to optional fields - It will clear the field.
* Allow checking if an optional field was set or not with `p/has-field?`.

###  VERSION [2.1.2]:
#### Changed
* return `:unrecognized` for unknown enum values in order to support forward compatibility.

#### Added
* CHANGELOG.md file  
