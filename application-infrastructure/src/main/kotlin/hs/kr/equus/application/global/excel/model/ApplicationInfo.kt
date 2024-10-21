package hs.kr.equus.application.global.excel.model

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ApplicationInfo {
    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("전형자료")

    fun getWorkbook(): Workbook {
        return workbook
    }

    fun getSheet(): Sheet {
        return sheet
    }

    fun format() {
        val row: Row = sheet.createRow(0)
        row.createCell(0).setCellValue("접수번호")
        row.createCell(1).setCellValue("전형유형")
        row.createCell(2).setCellValue("지역")
        row.createCell(3).setCellValue("추가유형")
        row.createCell(4).setCellValue("성명")
        row.createCell(5).setCellValue("생년월일")
        row.createCell(6).setCellValue("주소")
        row.createCell(7).setCellValue("전화번호")
        row.createCell(8).setCellValue("성별")
        row.createCell(9).setCellValue("학력구분")
        row.createCell(10).setCellValue("졸업년도")
        row.createCell(11).setCellValue("출신학교")
        row.createCell(12).setCellValue("반")
        row.createCell(13).setCellValue("보호자 성명")
        row.createCell(14).setCellValue("보호자 전화번호")

        // 3학년 2학기 성적
        row.createCell(15).setCellValue("국어 3학년 2학기")
        row.createCell(16).setCellValue("사회 3학년 2학기")
        row.createCell(17).setCellValue("역사 3학년 2학기")
        row.createCell(18).setCellValue("수학 3학년 2학기")
        row.createCell(19).setCellValue("과학 3학년 2학기")
        row.createCell(20).setCellValue("기술가정 3학년 2학기")
        row.createCell(21).setCellValue("영어 3학년 2학기")

        // 3학년 1학기 성적
        row.createCell(22).setCellValue("국어 3학년 1학기")
        row.createCell(23).setCellValue("사회 3학년 1학기")
        row.createCell(24).setCellValue("역사 3학년 1학기")
        row.createCell(25).setCellValue("수학 3학년 1학기")
        row.createCell(26).setCellValue("과학 3학년 1학기")
        row.createCell(27).setCellValue("기술가정 3학년 1학기")
        row.createCell(28).setCellValue("영어 3학년 1학기")

        // 직전 학기 성적
        row.createCell(29).setCellValue("국어 직전 학기")
        row.createCell(30).setCellValue("사회 직전 학기")
        row.createCell(31).setCellValue("역사 직전 학기")
        row.createCell(32).setCellValue("수학 직전 학기")
        row.createCell(33).setCellValue("과학 직전 학기")
        row.createCell(34).setCellValue("기술가정 직전 학기")
        row.createCell(35).setCellValue("영어 직전 학기")

        // 직전전 학기 성적
        row.createCell(36).setCellValue("국어 직전전 학기")
        row.createCell(37).setCellValue("사회 직전전 학기")
        row.createCell(38).setCellValue("역사 직전전 학기")
        row.createCell(39).setCellValue("수학 직전전 학기")
        row.createCell(40).setCellValue("과학 직전전 학기")
        row.createCell(41).setCellValue("기술가정 직전전 학기")
        row.createCell(42).setCellValue("영어 직전전 학기")

        // 종합 성적 및 기타 정보
        row.createCell(43).setCellValue("3학년 성적 총합")
        row.createCell(44).setCellValue("직전 학기 성적 총합")
        row.createCell(45).setCellValue("직전전 학기 성적 총합")
        row.createCell(46).setCellValue("교과성적환산점수")
        row.createCell(47).setCellValue("봉사시간")
        row.createCell(48).setCellValue("봉사점수")
        row.createCell(49).setCellValue("결석")
        row.createCell(50).setCellValue("지각")
        row.createCell(51).setCellValue("조퇴")
        row.createCell(52).setCellValue("결과")
        row.createCell(53).setCellValue("출석점수")
        row.createCell(54).setCellValue("대회")
        row.createCell(55).setCellValue("자격증")
        row.createCell(56).setCellValue("가산점")
        row.createCell(57).setCellValue("1차전형 총점")
        row.createCell(58).setCellValue("일반전형(170)")
        row.createCell(59).setCellValue("수험번호")
    }

}