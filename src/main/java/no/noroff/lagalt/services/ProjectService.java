package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;


public interface ProjectService extends CrudService<Project,Integer> {
    void addMembers(Integer[] userId, int id);
}
