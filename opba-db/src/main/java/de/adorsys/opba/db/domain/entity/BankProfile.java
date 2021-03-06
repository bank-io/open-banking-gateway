package de.adorsys.opba.db.domain.entity;

import de.adorsys.opba.db.domain.Approach;
import de.adorsys.opba.db.domain.converter.ScaApproachConverter;
import de.adorsys.opba.tppbankingapi.search.model.generated.BankProfileDescriptor;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_profile", uniqueConstraints = {@UniqueConstraint(columnNames = "bank_uuid", name = "opb_bank_profile_bank_uuid_key")})
public class BankProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final BankProfile.ToAspsp TO_ASPSP = Mappers.getMapper(BankProfile.ToAspsp.class);
    public static final BankProfile.ToBankProfileDescriptor TO_BANK_PROFILE_DESCRIPTOR =
            Mappers.getMapper(BankProfile.ToBankProfileDescriptor.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_profile_id_generator")
    @SequenceGenerator(name = "bank_profile_id_generator", sequenceName = "bank_profile_id_sequence")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_uuid", referencedColumnName = "uuid")
    private Bank bank;

    private String url;
    private String adapterId;
    private String idpUrl;

    @Convert(converter = ScaApproachConverter.class)
    private List<Approach> scaApproaches;

    @Enumerated(EnumType.STRING)
    private Approach preferredApproach;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bankProfile")
    @MapKey(name = "action")
    private Map<ProtocolAction, BankProtocol> actions = new HashMap<>();

    @Mapper
    public interface ToBankProfileDescriptor {
        @Mapping(source = "bank.name", target = "bankName")
        @Mapping(source = "bank.bic", target = "bic")
        @Mapping(source = "bank.uuid", target = "bankUuid")
        @Mapping(expression = "java(bankProfile.getActions().keySet().stream()"
                + ".map(Enum::name)"
                + ".collect(java.util.stream.Collectors.toList()))",
                target = "serviceList")
        BankProfileDescriptor map(BankProfile bankProfile);
    }

    @Mapper
    public interface ToAspsp {
        @Mapping(source = "bank.name", target = "name")
        @Mapping(source = "bank.bic", target = "bic")
        @Mapping(source = "bank.uuid", target = "bankCode")
        @Mapping(expression = "java("
                + "bankProfile.getScaApproaches().stream()"
                + ".map(a -> de.adorsys.xs2a.adapter.service.model.AspspScaApproach.valueOf(a.name()))"
                + ".collect(java.util.stream.Collectors.toList()))",
                target = "scaApproaches")
        Aspsp map(BankProfile bankProfile);
    }
}
