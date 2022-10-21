[1mdiff --git a/src/main/java/no/noroff/lagalt/dtos/details/ApplicationDetails.java b/src/main/java/no/noroff/lagalt/dtos/details/ApplicationDetails.java[m
[1mindex 05edb4f..7c81254 100644[m
[1m--- a/src/main/java/no/noroff/lagalt/dtos/details/ApplicationDetails.java[m
[1m+++ b/src/main/java/no/noroff/lagalt/dtos/details/ApplicationDetails.java[m
[36m@@ -12,4 +12,5 @@[m [mpublic class ApplicationDetails implements Serializable {[m
     private final String user;[m
     private final int application_id;[m
     private final String time;[m
[32m+[m[32m    private final String text;[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/no/noroff/lagalt/dtos/get/ApplicationGetDTO.java b/src/main/java/no/noroff/lagalt/dtos/get/ApplicationGetDTO.java[m
[1mindex 03d4047..469f1f0 100644[m
[1m--- a/src/main/java/no/noroff/lagalt/dtos/get/ApplicationGetDTO.java[m
[1m+++ b/src/main/java/no/noroff/lagalt/dtos/get/ApplicationGetDTO.java[m
[36m@@ -11,4 +11,5 @@[m [mpublic class ApplicationGetDTO {[m
     private UserDetails user;[m
     private ProjectDetails project;[m
     private String time;[m
[32m+[m[32m    private String text;[m
 }[m
[1mdiff --git a/src/main/java/no/noroff/lagalt/dtos/post/ApplicationPostDTO.java b/src/main/java/no/noroff/lagalt/dtos/post/ApplicationPostDTO.java[m
[1mindex 153cfff..3d07055 100644[m
[1m--- a/src/main/java/no/noroff/lagalt/dtos/post/ApplicationPostDTO.java[m
[1m+++ b/src/main/java/no/noroff/lagalt/dtos/post/ApplicationPostDTO.java[m
[36m@@ -9,4 +9,5 @@[m [mpublic class ApplicationPostDTO {[m
     private String user;[m
     private int project;[m
     private String time;[m
[32m+[m[32m    private String text;[m
 }[m
[1mdiff --git a/src/main/java/no/noroff/lagalt/models/Application.java b/src/main/java/no/noroff/lagalt/models/Application.java[m
[1mindex 6e9842b..4bac94a 100644[m
[1m--- a/src/main/java/no/noroff/lagalt/models/Application.java[m
[1m+++ b/src/main/java/no/noroff/lagalt/models/Application.java[m
[36m@@ -1,6 +1,7 @@[m
 package no.noroff.lagalt.models;[m
 [m
 [m
[32m+[m[32mimport jdk.jfr.Name;[m
 import jdk.jfr.Timestamp;[m
 import lombok.Getter;[m
 import lombok.Setter;[m
[36m@@ -30,4 +31,6 @@[m [mpublic class Application {[m
     @Timestamp[m
     public String time;[m
 [m
[32m+[m[32m    @Column(name = "motivational_text")[m
[32m+[m[32m    public String text;[m
 }[m
