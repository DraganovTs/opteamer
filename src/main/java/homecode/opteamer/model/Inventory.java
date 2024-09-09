package homecode.opteamer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventories")
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @Column(name = "asset_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @OneToOne
    @MapsId("asset")
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Integer count;
}
