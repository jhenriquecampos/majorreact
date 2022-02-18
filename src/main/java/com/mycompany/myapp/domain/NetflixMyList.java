package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NetflixMyList.
 */
@Entity
@Table(name = "netflix_my_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NetflixMyList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "movie_cod", nullable = false)
    private Integer movieCod;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NetflixMyList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMovieCod() {
        return this.movieCod;
    }

    public NetflixMyList movieCod(Integer movieCod) {
        this.setMovieCod(movieCod);
        return this;
    }

    public void setMovieCod(Integer movieCod) {
        this.movieCod = movieCod;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NetflixMyList user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NetflixMyList)) {
            return false;
        }
        return id != null && id.equals(((NetflixMyList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NetflixMyList{" +
            "id=" + getId() +
            ", movieCod=" + getMovieCod() +
            "}";
    }
}
