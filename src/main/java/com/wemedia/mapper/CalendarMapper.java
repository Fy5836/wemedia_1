package com.wemedia.mapper;

import com.wemedia.model.Calendar;
import com.wemedia.util.MyMapper;

import java.util.List;

public interface CalendarMapper extends MyMapper<Calendar> {

    List<Calendar> selectCalendar(Calendar schoolCalendar);


    Calendar selectByCalendarId(Integer id);


    int updateByCalendarId(Calendar SchoolCalendar);
}
