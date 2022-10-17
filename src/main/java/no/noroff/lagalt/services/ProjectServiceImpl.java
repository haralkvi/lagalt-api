package no.noroff.lagalt.services;

import no.noroff.lagalt.models.Project;
import no.noroff.lagalt.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private CommentService commentService;

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
    public Project update(Project updatedProject) {
        Project project = this.findById(updatedProject.getId());
        project.setName(updatedProject.getName());
        project.setCategory(updatedProject.getCategory());
        project.setStatus(updatedProject.getStatus());
        project.setSummary(updatedProject.getSummary());
        project.setLink(updatedProject.getLink());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (projectRepository.existsById(id)) {
            Project project = this.findById(id);

            // Users who have owned, been member of, applied to or viewed a
            // project are not deleted when project is deleted
            project.getOwner().getProjectsOwned().remove(project);
            project.getMembers().forEach(member -> member.getProjectsParticipated().remove(project));
            project.getUserViews().forEach(viewer -> viewer.getProjectsHistory().remove(project));
            project.getApplicants().forEach(applicant -> applicant.getProjectsAppliedTo().remove(project));

            // Comments belonging to a project are deleted when project is deleted
            project.getComments().forEach(comment -> commentService.delete(comment));

            // project can safely be deleted
            projectRepository.deleteById(id);
        }
    }

    @Override
    public void delete(Project entity) {
        projectRepository.delete(entity);
    }

    @Override
    public boolean existsById(Integer id) {
        return projectRepository.existsById(id);
    }

}
