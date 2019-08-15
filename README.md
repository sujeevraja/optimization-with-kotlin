# solver-examples
Examples of running different optimization solvers in Kotlin

## lp_solve

[lp_solve](http://lpsolve.sourceforge.net/5.5/) is an open source MIP solver.

### Installation (64-bit Linux)

The "using lp_solve 5.5 in Java programs" section in the project page (linked above) has a good
introduction on how to setup lp_solve in a Java project. We build on top of those instructions to
show how to setup a Kotlin project with lp_solve.

#### Preparing lp_solve library files

- Download the C++ library and Jar file archive from this [link](https://sourceforge.net/projects/lpsolve/).
- Note that these are two separate archives.
- For 64-bit Linux systems, the archive names may look like
    - lp_solve_5.5.2.5_dev_ux64.tar.gz
    - lp_solve_5.5.2.5_java.zip
- Choose a folder to house these files say "~/bin/lp_solve".
- Extract the C++ files into "~/bin/lp_solve/libs". This folder should now contain at least
    - liblpsolve55.a
    - liblpsolve55.so
- Extract the Java archive in the "~/bin/lp_solve" folder.
- It should have extracted into a new folder with a name like "lp_solve_5.5_java".
- Copy the file "lp_solve_5.5_java/lib/ux64/liblpsolve55j.so" to C++ libs folder
  ("~/bin/lp_solve/libs" in this example).
  
#### Pointing Gradle to lp_solve
  
Now that the lp_solve files have been prepared, we need to tell Gradle where to find them. Open
"~/.gradle/gradle.properties" and add the following two lines to the end of the file.

```
lpsolveJarPath=/path/to/lpsolve55j.jar
lpsolveLibPath=/path/to/lp_solve_5.5/libs
```

In our example, these paths would be "~/bin/lp_solve_5.5_java/lib/lpsolve55j.jar" and
"~/bin/lp_solve/libs". But gradle.properties does not understand the "~" character. So, absolute
paths need to be specified.

#### Using lp_solve in a Gradle project with Kotlin build script

If starting fresh, create a Gradle application project with `gradle init` and selecting type as
"application". The language may be Java or Kotlin. The rest of the instructions are identical for
both languages. For an existing project, ensure that it includes the
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

