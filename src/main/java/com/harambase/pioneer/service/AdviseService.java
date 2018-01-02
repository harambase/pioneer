package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Advise;

public interface AdviseService {

    HaramMessage advisingList(int start, int length, String search, String order, String orderCol, String studentId, String facultyId);

    HaramMessage updateAdvise(Integer id, String studentId, String facultyId);

    HaramMessage assignMentor(Advise advise);

    HaramMessage removeMentor(Integer id);

    HaramMessage getMentor(Integer id);
}
