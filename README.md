# solver-examples
Examples of running different optimization solvers in Kotlin.

## lp_solve

[lp_solve](http://lpsolve.sourceforge.net/5.5/) is an open source MIP solver.

### Installation (64-bit Linux)

The "using lp_solve 5.5 in Java programs" section in the project page (linked above) has a good
introduction on how to setup lp_solve in a Java project. We build on top of those instructions to
show how to setup a Kotlin project with lp_solve.

#### Preparing library files

- Download the C++ library and JAR file archive [here](https://sourceforge.net/projects/lpsolve/).
- Note that these are two separate archives.
- For 64-bit Linux systems, the archive names may look like
    - lp_solve_5.5.2.5_dev_ux64.tar.gz
    - lp_solve_5.5.2.5_java.zip
- Choose a folder to house these files say "$HOME/bin/lp_solve".
- Extract the C++ files into "$HOME/bin/lp_solve/libs". This folder should now contain at least
    - liblpsolve55.a
    - liblpsolve55.so
- Extract the Java archive in the "$HOME/bin/lp_solve" folder.
- It should have extracted into a new folder with a name like "lp_solve_5.5_java".
- Copy the file "lp_solve_5.5_java/lib/ux64/liblpsolve55j.so" to C++ libs folder
  ("$HOME/bin/lp_solve/libs" in this example).
  
#### Pointing Gradle to JAR/library files
  
Now that the lp_solve files have been prepared, we need to tell Gradle where to find them. Open
"$HOME/.gradle/gradle.properties" and add the following two lines to the end of the file.

```
lpsolveJarPath=/path/to/lpsolve55j.jar
lpsolveLibPath=/path/to/lp_solve_5.5/libs
```

In our example, these paths would be "$HOME/bin/lp_solve_5.5_java/lib/lpsolve55j.jar" and
"$HOME/bin/lp_solve/libs". But gradle.properties may not understand "$HOME". So, absolute
paths need to be specified (i.e. something like "/home/username/bin/ortools/lib").

#### Using in Gradle project

We assume that the Gradle project will use the kotlin build script. If starting fresh, create a
Gradle application project with `gradle init` and selecting type as "application". The language may
be Java or Kotlin. The rest of the instructions are identical for both languages. For an existing
project, ensure that it includes the
[application](https://docs.gradle.org/current/userguide/application_plugin.html) plugin.

In "build.gradle.kts", add the following two lines within the "dependencies" block:

```
    val lpsolveJarPath: String by project
    compile(files(lpsolveJarPath))
```

Add the following two lines in the "application" block:

```
    val lpsolveLibPath: String by project
    applicationDefaultJvmArgs = listOf(
        "-Djava.library.path=$lpsolveLibPath"
    )
```

That's it. You should be able to use `import lpsolve.*` and run the Java example shown in the
"Using lpsolve from Java" section on the lp_solve website.

## Google OR-Tools

[Google Or-Tools](https://developers.google.com/optimization/) is a wrapper suite for optimization
and an easy way to call solvers like CLP and CBC from Java/Kotlin.

### Installation (64-bit Linux)

Following instructions are based on this [SO page](https://stackoverflow.com/questions/52518654/installing-google-or-tools-with-gradle-project-on-windows-10-intellij-idea/52790798#52790798).

#### Preparing library files

- Download Java binary sources [here](https://developers.google.com/optimization/install/java/linux).
- The archive should have a name like "or-tools_ubuntu-18.04_v7.2.6977.tar.gz".
- Choose a folder to house these files say "$HOME/bin/ortools".
- Extract the archive inside this folder.
- You should see a folder named "$HOME/bin/ortools/lib" that contains at least the following files:
    - com.google.ortools.jar
    - protobuf.jar
    - a bunch of ".a" and ".so" files (many of these are solver libraries).
  
#### Pointing Gradle to OR-Tools
  
Now that the lib/JAR files have been prepared, we need to tell Gradle where to find them. Open
"$HOME/.gradle/gradle.properties" and add the following lines to the end of the file.

```
ortoolsLibPath=/path/to/ortools_lib_folder
```

In our example, the above path would be "$HOME/bin/ortools/lib". But gradle.properties may not
understand the "$HOME" character. So, absolute paths need to be specified (i.e. something like
"/home/username/bin/ortools/lib").

#### Using in Gradle project

We assume that the Gradle project will use the kotlin build script. If starting fresh, create a
Gradle application project with `gradle init` and selecting type as "application". The language may
be Java or Kotlin. The rest of the instructions are identical for both languages. For an existing
project, ensure that it includes the
[application](https://docs.gradle.org/current/userguide/application_plugin.html) plugin.

In "build.gradle.kts", add the following two lines within the "dependencies" block:

```
    val ortoolsLibPath: String by project
    compile(files("$ortoolsLibPath/com.google.ortools.jar"))
    compile(files("$ortoolsLibPath/protobuf.jar"))

```

If "java.library.path" has not been defined in the "application" block, add the following lines to
the "application" block:

```
    val ortoolsLibPath: String by project // ortools library files
    applicationDefaultJvmArgs = listOf(
        "-Djava.library.path=$lpsolveLibPath:$ortoolsLibPath"
    )
```

If it has already been defined, append the value of the "ortoolsLibPath" variable to the
"-Djava.library.path" string with an appropriate separator (; for windows, : for linux).
For example,

```
    val lpsolveLibPath: String by project
    val ortoolsLibPath: String by project
    applicationDefaultJvmArgs = listOf(
        "-Djava.library.path=$lpsolveLibPath:$ortoolsLibPath"
    )
```

Now, you can run the introductory [Java example](https://developers.google.com/optimization/introduction/java)
in Java or Kotlin. This repo has a Kotlin example.

#### Alternate way to use protobuf

Protobuf is a dependency for OR-Tools and comes packaged within the OR-Tools archive. If you want
to add protobuf directly to the Gradle project as a library, that can also be done. Remove the line

```
    compile(files("$ortoolsLibPath/protobuf.jar"))
```

and add the following line inside the "dependencies" block in build.gradle.kts:

```
    implementation("com.google.protobuf:protobuf-java:3.0.0")
```

Note that in this way, we need to be sure that the version of protobuf we use here is the same as
that used by OR-Tools.

