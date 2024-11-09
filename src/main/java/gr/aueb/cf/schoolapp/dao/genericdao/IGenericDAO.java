package gr.aueb.cf.schoolapp.dao.genericdao;

import java.util.List;
import java.util.Map;

public interface IGenericDAO<T> {
    T insert(T t);
    T update(T t);
    void delete(Object id);
    T getById(Object id);
    List<T> getAll();
    <K extends T> List<K> getByCriteria(Class<K> clazz, Map<String, Object> criteria);
}
