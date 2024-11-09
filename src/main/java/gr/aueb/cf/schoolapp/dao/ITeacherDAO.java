package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Teacher;

import java.util.List;

public interface ITeacherDAO {
    /**
     * adds an instance of Teacher to DB
     * @param teacher
     * @return the inserted instance
     */
    Teacher insert(Teacher teacher);

    /**
     * updates an instance of Teacher to DB
     * @param teacher
     * @return the updated instance
     */
    Teacher update(Teacher teacher);

    /**
     * deletes an instance of Teacher from DB
     * @param id
     */
    void delete(Long id);

    /**
     * selects a list of Teacher instances based on lastame
     * @return the selected list
     */
    List<Teacher> getByLastname(String lastname);

    /**
     * selects a Teacher instance based on id
     * @return the selected Teacher instance
     */
    Teacher getById(Long id);
}
