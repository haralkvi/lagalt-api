package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Application;
import no.noroff.lagalt.models.Comment;
import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CommentService commentService;

    //javadocs

    @Override
    public Project findById(Integer integer) {
        return projectRepository.findById(integer).get();
    }

    @Override
    public Collection<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project add(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    public Project update(Project entity) {
        return projectRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (projectRepository.existsById(id)) {
            Project project = this.findById(id);

            User projectOwner = project.getOwner();
            Collection<User> projectMembers = project.getMembers();
            Collection<User> projectViewers = project.getUserViews();
            Collection<Application> projectApplications = project.getApplications();
            Collection<Comment> projectComments = project.getComments();

            // Users who have owned, been member of or viewed a project are not deleted when project is deleted
            projectOwner.getProjectsOwned().remove(project);
            projectMembers.forEach(member -> member.getProjectsParticipated().remove(project));
            projectViewers.forEach(viewer -> viewer.getProjectsHistory().remove(project));

            // Applications and comments belonging to a project are deleted when project is deleted
            projectApplications.forEach(application -> applicationService.delete(application));
            projectComments.forEach(comment -> commentService.delete(comment));

            // project can safely be deleted
            projectRepository.deleteById(id);
        }
    }

    @Override
    public void delete(Project entity) {
        projectRepository.delete(entity);
    }
}
