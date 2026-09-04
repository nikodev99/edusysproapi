package com.edusyspro.api.service.interfaces;

import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.dto.custom.ExamProgress;
import com.edusyspro.api.dto.custom.ExamResponse;
import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.ExamType;

import java.util.List;

public interface ExamService {

    ExamDTO saveExam(ExamDTO examDTO);

    int updateExam(int examId, UpdateField field);

    List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear);

    ExamDTO findClasseExamsAssignments(Integer examId, Integer classeId, String academicYear);

    ExamResponse findClasseExamWithCalculations(Integer examId, Integer classeId, String academicYear, boolean onlyStat);
    List<ExamResponse> findClasseAllExamWithCalculations(Integer classeId, String academicYear, boolean onlyStat);

    List<ExamProgress> getStudentExamProgress(String studentId, Integer classeId, String academicYear);

    ExamDTO findStudentExamsAssignments(Integer examId, Integer classeId, String academicYear, String studentId);

    ExamResponse findStudentExamWithCalculations(Integer examId, Integer classeId, String academicYear, String studentId, boolean onlyStat);
    List<ExamResponse> findStudentExamWithCalculations(Integer classeId, String academicYear, String studentId, boolean onlyStat);

    ExamDTO findExamById(Integer examId);

    List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear);

    boolean deleteExam(Integer examId);

    boolean checkExamHasAssignments(Integer examId);

    ///EXAM TYPE METHODS
    ExamType saveExamType(ExamType examType);
    int updateExamType(int examTypeId, UpdateField field);
    List<ExamType> findAllExamTypes(String schoolId);
    boolean deleteExamType(int examTypeId);
}
