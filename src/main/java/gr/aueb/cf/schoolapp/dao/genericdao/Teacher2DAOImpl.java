package gr.aueb.cf.schoolapp.dao.genericdao;

import gr.aueb.cf.schoolapp.model.Teacher;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class Teacher2DAOImpl extends AbstractDAO<Teacher> implements ITeacher2DAO {

    //every dao set its state
    public Teacher2DAOImpl() {
        this.setPersistentClass(Teacher.class);
    }
}
