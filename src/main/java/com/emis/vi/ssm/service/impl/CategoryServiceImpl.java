package com.emis.vi.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emis.vi.ssm.mapper.CategoryMapper;
import com.emis.vi.ssm.pojo.Category;
import com.emis.vi.ssm.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
  @Autowired
  CategoryMapper categoryMapper;

  public List<Category> list() {
    return categoryMapper.list();
  }

}
