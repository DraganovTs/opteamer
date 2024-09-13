package homecode.opteamer.mapper;

import homecode.opteamer.model.PreOperativeAssessment;
import homecode.opteamer.model.dtos.PreOperativeAssessmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PreOperativeAssessmentMapper {

    PreOperativeAssessmentMapper INSTANCE = Mappers.getMapper(PreOperativeAssessmentMapper.class);

    PreOperativeAssessmentDTO toPreOperativeAssessmentDTO(PreOperativeAssessment preOperativeAssessment);

    PreOperativeAssessment toPreOperativeAssessment(PreOperativeAssessmentDTO preOperativeAssessmentDTO);
}
