/**
 * Copyright © 2011 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Delegates.
 *
 * FenixEdu Delegates is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Delegates is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Delegates.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ist.fenixedu.delegates.ui;

/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.fenixedu.academic.domain.CurricularCourse;
import org.fenixedu.academic.domain.Enrolment;
import org.fenixedu.academic.domain.ExecutionSemester;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.student.Student;

public class DelegateCurricularCourseBean {
    private CurricularCourse curricularCourse;

    private ExecutionYear executionYear;

    private ExecutionSemester executionSemester;

    private List<Student> enrolledStudents;

    private Integer curricularYear;

    static final public Comparator CURRICULAR_COURSE_COMPARATOR_BY_CURRICULAR_YEAR_AND_CURRICULAR_SEMESTER =
            new ComparatorChain();
    static {
        ((ComparatorChain) CURRICULAR_COURSE_COMPARATOR_BY_CURRICULAR_YEAR_AND_CURRICULAR_SEMESTER)
                .addComparator(new BeanComparator("curricularYear"));
        ((ComparatorChain) CURRICULAR_COURSE_COMPARATOR_BY_CURRICULAR_YEAR_AND_CURRICULAR_SEMESTER)
                .addComparator(new BeanComparator("curricularSemester"));
    }

    public DelegateCurricularCourseBean(CurricularCourse curricularCourse, ExecutionYear executionYear, Integer curricularYear,
            ExecutionSemester executionSemester) {
        setCurricularCourse(curricularCourse);
        setExecutionYear(executionYear);
        setCurricularYear(curricularYear);
        setExecutionPeriod(executionSemester);
    }

    public CurricularCourse getCurricularCourse() {
        return (curricularCourse);
    }

    public void setCurricularCourse(CurricularCourse curricularCourse) {
        this.curricularCourse = curricularCourse;
    }

    public ExecutionYear getExecutionYear() {
        return (executionYear);
    }

    public void setExecutionYear(ExecutionYear executionYear) {
        this.executionYear = executionYear;
    }

    public ExecutionSemester getExecutionPeriod() {
        return (executionSemester);
    }

    public void setExecutionPeriod(ExecutionSemester executionSemester) {
        this.executionSemester = executionSemester;
    }

    public List<Student> getEnrolledStudents() {
        List<Student> result = new ArrayList<Student>();
        for (Student studentDR : this.enrolledStudents) {
            result.add(studentDR);
        }
        return result;
    }

    public void setEnrolledStudents(List<Student> students) {
        this.enrolledStudents = new ArrayList<Student>();
        for (Student student : students) {
            this.enrolledStudents.add(student);
        }
    }

    public void calculateEnrolledStudents() {
        List<Student> enrolledStudents = new ArrayList<Student>();
        for (Enrolment enrolment : getCurricularCourse().getEnrolmentsByAcademicInterval(
                getExecutionPeriod().getAcademicInterval())) {
            enrolledStudents.add(enrolment.getRegistration().getStudent());
        }
        Collections.sort(enrolledStudents, Student.NUMBER_COMPARATOR);
        setEnrolledStudents(enrolledStudents);
    }

    public Integer getSemester() {
        return getCurricularSemester();
    }

    public Integer getCurricularSemester() {
        return getExecutionPeriod().getSemester();
    }

    public Integer getCurricularYear() {
        return curricularYear;
    }

    public void setCurricularYear(Integer curricularYear) {
        this.curricularYear = curricularYear;
    }

    public Integer getEnrolledStudentsNumber() {
        if (this.enrolledStudents != null) {
            return this.enrolledStudents.size();
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DelegateCurricularCourseBean ? equals((DelegateCurricularCourseBean) obj) : false;
    }

    public boolean equals(DelegateCurricularCourseBean delegateCurricularCourseBean) {
        return getCurricularCourse().equals(delegateCurricularCourseBean.getCurricularCourse())
                && getCurricularYear().equals(delegateCurricularCourseBean.getCurricularYear())
                && getExecutionPeriod().equals(delegateCurricularCourseBean.getExecutionPeriod())
                && getExecutionYear().equals(delegateCurricularCourseBean.getExecutionYear());
    }

    @Override
    public int hashCode() {
        return getCurricularCourse().hashCode() + getCurricularYear().hashCode() + getExecutionPeriod().hashCode()
                + getExecutionYear().hashCode();
    }
}