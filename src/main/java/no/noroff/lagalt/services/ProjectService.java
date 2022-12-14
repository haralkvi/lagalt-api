package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;


public interface ProjectService extends CrudService<Project,Integer> {
    void addTags(String[] tags, int id);

    void addSkills(String[] skills, int id);
}
