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

So there are two ways to query SPARQL endpoints:
* with apache jena, version 2.13.0, from [jena-droid](https://github.com/sbrunk/jena-android)
* with (simpler) [SPARQL-ANDROID](https://github.com/BorderCloud/SPARQL-ANDROID), fork of [SPARQL-JAVA](https://github.com/BorderCloud/SPARQL-JAVA)


### Authors

* Izabela Stechnij
* Dominik Sepioło
* Paweł Płatek