package com.edusyspro.api.model.enums;

public enum PlanningStatus {
    /**
     * Represents a regular school or teaching day in the scheduling context.
     * This type of day is designated for standard educational sessions and
     * instructional activities as part of the planned academic calendar.
     */
    TEACHING_DAY,

    /**
     * Represents a public holiday in the scheduling context.
     * This designation indicates a day officially recognized as a holiday,
     * during which regular academic or operational activities are typically suspended.
     */
    PUBLIC_HOLIDAY,

    /**
     * Represents a designated school holiday in the scheduling context.
     * This status is used to identify days that are officially set aside
     * when no academic or operational activities occur, typically as part
     * of an institution's planned yearly calendar.
     */
    SCHOOL_HOLIDAY,

    /**
     * Represents a weekend in the scheduling context.
     * This designation typically applies to Saturdays and Sundays,
     * which are considered non-working or non-academic days in many institutions,
     * unless otherwise specified based on locale or organizational policies.
     */
    WEEKEND,

    /**
     * Represents a period designated for exams in the scheduling context.
     * This status is used to indicate days specifically allocated for
     * conducting academic examinations as part of an institution's calendar.
     */
    EXAM_PERIOD,

    /**
     * Indicates that the corresponding day, event, or activity is suspended.
     * This status is generally used to signify a temporary halt in planned
     * operations or activities due to specific circumstances or decisions.
     */
    SUSPENDED,

    /**
     * Represents a makeup day in the scheduling context.
     * A makeup day is typically designated for recovering missed instructional
     * or operational activities. This status is used to identify days that
     * are set aside to compensate for cancellations or interruptions in
     * the planned schedule, ensuring the fulfillment of academic or institutional
     * obligations.
     */
    MAKEUP_DAY
}
