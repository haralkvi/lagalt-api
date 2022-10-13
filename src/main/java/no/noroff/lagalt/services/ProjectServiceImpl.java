package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.models.User;
import no.noroff.lagalt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    //javadocs

    @Override
    public Project findById(Integer integer) {
        Optional<Project> opt = projectRepository.findById(integer);
        return opt.orElse(null);
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

            // Users who have owned, been member of or viewed a project are not deleted when project is deleted
            project.getOwner().getProjectsOwned().remove(project);
            project.getMembers().forEach(member -> member.getProjectsParticipated().remove(project));
            project.getUserViews().forEach(viewer -> viewer.getProjectsHistory().remove(project));

            // Applications and comments belonging to a project are deleted when project is deleted
            project.getApplications().forEach(application -> applicationService.delete(application));
            project.getComments().forEach(comment -> commentService.delete(comment));

            // project can safely be deleted
            projectRepository.deleteById(id);
        }
    }

    @Override
    public void delete(Project entity) {
        projectRepository.delete(entity);
    }

    public void addMembers(Integer[] userId, int id){
        Project project = this.findById(id);
        Set<User> users = project.getMembers();
        for(int s : userId){
            User user = userService.findById(s);
            users.add(user);
        }
        project.setMembers(users);
        projectRepository.save(project);
    }
}
