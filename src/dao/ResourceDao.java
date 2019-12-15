package dao;

import domain.Resource;

import java.util.List;
import java.util.Map;

public interface ResourceDao {
    int add(Resource learnResouce);
    int update(int id,Resource learnResouce);
    int deleteById(int id);
    Resource queryLearnResouceById(int id);
    List<Resource> queryLearnResouceList(int offset, int limit);
}
