# soundfiles

Collection of free sound effects files.

## Sound Format

The desired sound format is ogg vorbis to save space and avoid licensing issues.

## LICENSES

All files below src/main/resources/com/github/jjYBdx4IL/audio/soundfiles/pd are in the public domain.

Further directories for compatible licenses may be added.

For credits and required documentation have a look at the VCS log. Currently, a git log dump is included with
every build.

## CONTRIBUTIONS

If you want to contribute files, please respect the license demands of the sound file's author(s). This project's aim
is to include only files that do not require changes to dependent programs or restrict them or their licensing in any
way. However, we cannot give any guarantees of course.

If you want to make contributions, please follow the conventions as demonstrated in the current git log, ie.

* one sound file per commit;
* if possible, include information about the original author, the web page's url and the downloaded file's name, and,
of course, the license;
* re-encode the file to ogg vorbis using a reasonable bit rate (not larger than 128 kbits/s);
* rename the file according to Java camel case conventions without spaces etc. and remove cryptic numbers so we have
a nice name ending in lower case '.ogg' (again, try to use existing stuff as a reference to avoid chaos).

## ResourceBundle

We automatically create a resource bundle for all .ogg files in order to provide code lookup in IDEs to avoid users
having to manually determine the asset paths.

The compilation and running of the resource bundle generator within the same maven module is a bit messy and should
be avoided, ie. it should be moved to its own module at some point.

--
devel/java/github/soundfiles@7840
