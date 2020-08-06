package com.emis.vi.ssm.mapper;

import java.util.List;

import com.emis.vi.ssm.pojo.Category;
import com.emis.vi.ssm.util.Page;

public interface CategoryMapper {

  public int add(Category category);

  public void delete(int id);

  public Category get(int id);

  public int update(Category category);

  public List<Category> list();

}
