package co.th.natdanai.redis.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@Data
public class Tutorial {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @Column(name = "title")
        private String title;

        @Column(name = "description")
        private String description;

        @Column(name = "published")
        private boolean published;

}
