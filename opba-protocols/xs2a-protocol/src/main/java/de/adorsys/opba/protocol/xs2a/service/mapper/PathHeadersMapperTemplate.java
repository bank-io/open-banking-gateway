package de.adorsys.opba.protocol.xs2a.service.mapper;

import de.adorsys.opba.protocol.xs2a.service.dto.PathHeadersToValidate;
import de.adorsys.opba.protocol.xs2a.service.dto.ValidatedPathHeaders;
import de.adorsys.opba.protocol.xs2a.service.xs2a.context.BaseContext;
import de.adorsys.opba.protocol.xs2a.service.xs2a.dto.DtoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PathHeadersMapperTemplate<C extends BaseContext, P, H> {

    private final DtoMapper<? super C, H> toHeaders;
    private final DtoMapper<? super C, P> toPath;

    public PathHeadersToValidate<P, H> forValidation(C context) {
        return new PathHeadersToValidate<>(
                toPath.map(context),
                toHeaders.map(context)
        );
    }

    public ValidatedPathHeaders<P, H> forExecution(C context) {
        return new ValidatedPathHeaders<>(
                toPath.map(context),
                toHeaders.map(context)
        );
    }
}
