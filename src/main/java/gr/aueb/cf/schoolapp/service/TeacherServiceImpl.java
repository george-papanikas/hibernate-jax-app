package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dao.genericdao.ITeacher2DAO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.*;

@Provider
@ApplicationScoped
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    // Auto-wiring
    @Inject
    private ITeacherDAO teacherDAO;

    @Inject
    private ITeacher2DAO teacher2DAO;

    @Override
    public Teacher insertTeacher(TeacherInsertDTO dto) throws Exception {
        Teacher teacher;
        try {
            JPAHelper.beginTransaction();
            teacher =mapInsert(dto);
            //teacher = teacherDAO.insert(teacher);
            teacher = teacher2DAO.insert(teacher);
            if (teacher.getId() == null) {
                throw new Exception("Insert Error");
            }
            JPAHelper.commitTransaction();
            logger.info("Teacher with id" + teacher.getId() + " inserted");
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Insert teacher rollback - Error" + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }

private Teacher mapInsert(TeacherInsertDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setId(null);
        teacher.setFirstname(dto.getFirstname());
        teacher.setLastname(dto.getLastname());
        return teacher;
}

    private Teacher mapUpdate(TeacherUpdateDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getId());
        teacher.setFirstname(dto.getFirstname());
        teacher.setLastname(dto.getLastname());
        return teacher;
    }

    @Override
    public Teacher updateTeacher(TeacherUpdateDTO dto) throws EntityNotFoundException {
        Teacher teacherToUpdate;
        Teacher updatedTeacher;
        try {
            JPAHelper.beginTransaction();
            teacherToUpdate = mapUpdate(dto);

            if (teacherDAO.getById(teacherToUpdate.getId()) == null) {
                throw new EntityNotFoundException(Teacher.class, teacherToUpdate.getId());
            }
            //updatedTeacher = teacherDAO.update(teacherToUpdate);
            updatedTeacher = teacher2DAO.update(teacherToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id" + updatedTeacher.getId() + " updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Update teacher rollback - Error" + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return updatedTeacher;
    }

    @Override
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.beginTransaction();
            if (teacherDAO.getById(id) == null) {
                throw new EntityNotFoundException(Teacher.class, id);
            }
            //teacherDAO.delete(id);
            teacher2DAO.delete(id);
            JPAHelper.commitTransaction();
            logger.info("Teacher with id" + id + " deleted");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Delete teacher rollback - Error" + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Teacher> getTeachersByLastname(String lastname)
            throws EntityNotFoundException {
        List<Teacher> teachers;
        try {
            JPAHelper.beginTransaction();
/*            teachers = teacherDAO.getByLastname(lastname);
            if (teachers.size() == 0) {
                throw new EntityNotFoundException(List.class, 0L);
            }*/
            Map<String, Object> criteria = new HashMap<>();
            criteria.put("lastname", lastname);
            teachers = Optional.of(teacher2DAO.getByCriteria(Teacher.class, criteria))
                            .orElseThrow(() -> new EntityNotFoundException(List.class, 0L));
            JPAHelper.commitTransaction();
            logger.info("Teachers with lastname " + lastname + " found");
            // return teachers; not right because in case teachers = null would be returned
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("getTeachersByLastname rollback - Error" + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(Long id) throws EntityNotFoundException {
        Teacher teacher;
        try {
            JPAHelper.beginTransaction();
            /*teacher = teacherDAO.getById(id);
            if (teacher == null) {
                throw new EntityNotFoundException(List.class, 0L);
            }*/
            teacher = Optional.ofNullable(teacher2DAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Teacher.class, id));
            JPAHelper.commitTransaction();
            // return teacher; not right because in case teacher = null would be returned
            logger.info("Teacher with id " + id + " found");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("getTeachersById rollback - Error" + e.getMessage());
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return teacher;
    }
}
