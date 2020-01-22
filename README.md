# AGH - workshop, semantic web, mobile applications project


### Install and run
```bash
git clone --recurse-submodules https://github.com/GrosQuildu/agh_ws_semweb_aml
cd agh_ws_semweb_aml/WSSWAML
./gradlew build  # etc, or use android studio
```


### Project structure

```
.
└── WSSWAML - project root, gradle + android studio files
    ├── app - application sources and stuff
    │   └── libs - mostly apache jena jars
    ├── SPARQL-ANDROID - git submodule, external library from GitHub
```

Query SPARQL endpoints:
* With apache jena (currently used), version 2.13.0, from [jena-droid](https://github.com/sbrunk/jena-android)
* With (simpler) [SPARQL-ANDROID](https://github.com/BorderCloud/SPARQL-ANDROID), fork of [SPARQL-JAVA](https://github.com/BorderCloud/SPARQL-JAVA)

Localize user:
* `LocationListener` service is implemented (uses both GPS and Network)
* Accessible via singleton that (un)bound the service
* Enabled/disabled automatically by activities

### Authors

* Izabela Stechnij
* Dominik Sepioło
* Paweł Płatek
