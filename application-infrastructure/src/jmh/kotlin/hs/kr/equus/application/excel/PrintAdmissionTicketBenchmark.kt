package hs.kr.equus.application.excel

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import hs.kr.equus.application.domain.file.spi.GetObjectPort
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import hs.kr.equus.application.domain.school.model.School
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.global.excel.generator.PrintAdmissionTicketGenerator
import org.mockito.Mockito
import org.openjdk.jmh.annotations.*
import org.springframework.mock.web.MockHttpServletResponse
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletResponse

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class PrintAdmissionTicketBenchmark {
    private lateinit var generator: PrintAdmissionTicketGenerator
    private var applicationInfoVOList: List<ApplicationInfoVO> = arrayListOf()

    @Setup
    fun init() {
        val mockApplicationService = Mockito.mock(ApplicationService::class.java)
        val mockGetObjectPort = Mockito.mock(GetObjectPort::class.java)
        val mockHttpServletResponse = MockHttpServletResponse()
        generator = PrintAdmissionTicketGenerator(mockHttpServletResponse, mockApplicationService, mockGetObjectPort)

        val applicationStub = Application(
            receiptCode = 1,
            sex = Sex.MALE,
            applicationType = ApplicationType.COMMON,
            userId = java.util.UUID.randomUUID(),
            isDaejeon = true,
            isOutOfHeadcount = false,
            birthDate = java.time.LocalDate.now(),
            photoPath = "image.png",
            educationalStatus = EducationalStatus.GRADUATE,
            applicantName = "홍길동",
            applicantTel = "01012345678",
            parentRelation = "모",
            parentName = "김모",
            parentTel = "01087654321",
            streetAddress = "대전광역시 중구 중앙로 76",
            postalCode = "34940",
            detailAddress = "대전광역시 중구 중앙로 76",
            applicationRemark = ApplicationRemark.NOTHING,
            veteransNumber = null,
            studyPlan = "공부",
            selfIntroduce = "자기소개"
        )

        val graduationStub = Graduation(
            id = 1,
            graduateDate = java.time.YearMonth.now(),
            isProspectiveGraduate = true,
            receiptCode = 1,
            studentNumber = StudentNumber(
                gradeNumber = "3",
                classNumber = "1",
                studentNumber = "8"
            ),
            schoolCode = "8092018",
            teacherName = "홍길동",
            teacherTel = "01012345678"
        )

        val graduationCaseStub = GraduationCase(
            id = 1,
            receiptCode = 1,
            extraScoreItem = ExtraScoreItem(
                hasCertificate = false,
                hasCompetitionPrize = false
            ),
            volunteerTime = 10,
            absenceDayCount = 0,
            lectureAbsenceCount = 0,
            latenessCount = 0,
            earlyLeaveCount = 0,
            koreanGrade = "AAAA",
            socialGrade = "AAAA",
            historyGrade = "AAAA",
            mathGrade = "AAAA",
            scienceGrade = "AAAA",
            englishGrade = "AAAA",
            techAndHomeGrade = "AAAA",
            isProspectiveGraduate = true
        )

        val score = Score(
            id = 1,
            receiptCode = 1,
            thirdScore = BigDecimal(0),
            thirdBeforeScore = BigDecimal(20),
            thirdBeforeBeforeScore = BigDecimal(20),
            thirdGradeScore = BigDecimal(40),
            totalGradeScore = BigDecimal(80),
            attendanceScore = 15,
            volunteerScore = BigDecimal(15),
            totalScore = BigDecimal(170),
        )

        val school = School(
            code = "8092018",
            name = "대전중학교",
            address = "대전광역시 중구 중앙로 76",
            tel = "0421234567",
            type = "중학교",
            regionName = "대전"
        )

        val applicationVo = ApplicationInfoVO(
            application = applicationStub,
            graduationInfo = graduationStub,
            applicationCase = graduationCaseStub,
            score = score,
            school = school
        )

        applicationInfoVOList = List(100) { applicationVo }
    }

    @Benchmark
    fun benchmarkGenerate() {
        generator.generate(applicationInfoVOList)
    }

    @Benchmark
    fun benchmarkLoadSourceWorkbook() {
        generator.loadSourceWorkbook()
    }

    @Benchmark
    fun benchmarkCreateStyleMap() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        generator.createStyleMap(sourceWorkbook, targetWorkbook)
    }

    @Benchmark
    fun benchmarkCopyRows() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        val sourceSheet = sourceWorkbook.getSheetAt(0)
        val targetSheet = targetWorkbook.createSheet("수험표")
        val styleMap = generator.createStyleMap(sourceWorkbook, targetWorkbook)
        generator.copyRows(sourceSheet, targetSheet, 0, 16, 0, styleMap)
    }

    @Benchmark
    fun benchmarkCopyRow() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        val sourceSheet = sourceWorkbook.getSheetAt(0)
        val targetSheet = targetWorkbook.createSheet("수험표")
        val styleMap = generator.createStyleMap(sourceWorkbook, targetWorkbook)
        val sourceRow = sourceSheet.getRow(0)
        val targetRow = targetSheet.createRow(0)
        generator.copyRow(sourceSheet, targetSheet, sourceRow, targetRow, styleMap)
    }

    @Benchmark
    fun benchmarkCopyCell() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        val sourceSheet = sourceWorkbook.getSheetAt(0)
        val targetSheet = targetWorkbook.createSheet("수험표")
        val styleMap = generator.createStyleMap(sourceWorkbook, targetWorkbook)
        val sourceRow = sourceSheet.getRow(0)
        val sourceCell = sourceRow.getCell(0)
        val targetRow = targetSheet.createRow(0)
        val targetCell = targetRow.createCell(0)
        generator.copyCell(sourceCell, targetCell, styleMap)
    }

    @Benchmark
    fun benchmarkFillApplicationData() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val sourceSheet = sourceWorkbook.getSheetAt(0)
        generator.fillApplicationData(sourceSheet, 0, applicationInfoVOList[0], sourceWorkbook)
    }

    @Benchmark
    fun benchmarkCopyImage() {
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        val targetSheet = targetWorkbook.createSheet("수험표")
        val imageBytes = ByteArray(0)  // Mocked image byte array
        generator.copyImage(imageBytes, targetSheet, 0)
    }

    @Benchmark
    fun benchmarkSetValue() {
        val sourceWorkbook = generator.loadSourceWorkbook()
        val sourceSheet = sourceWorkbook.getSheetAt(0)
        generator.setValue(sourceSheet, "A1", "Test Value")
    }

    @Benchmark
    fun benchmarkInsertImageToCell() {
        val targetWorkbook = org.apache.poi.xssf.streaming.SXSSFWorkbook()
        val targetSheet = targetWorkbook.createSheet("수험표")
        val imageBytes = ByteArray(0)  // Mocked image byte array
        generator.insertImageToCell(targetSheet, 0, 0, imageBytes)
    }
}
