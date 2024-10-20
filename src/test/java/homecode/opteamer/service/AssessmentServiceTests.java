package homecode.opteamer.service;

import homecode.opteamer.repository.AssessmentRepository;
import homecode.opteamer.repository.PatientRepository;
import homecode.opteamer.repository.PreOperativeAssessmentRepository;
import homecode.opteamer.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTests {

    @Mock
    private AssessmentRepository assessmentRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private PreOperativeAssessmentRepository preOperativeAssessmentRepository;
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    public void setUp() {

    }
}
