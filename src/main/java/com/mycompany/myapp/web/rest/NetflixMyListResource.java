package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.NetflixMyList;
import com.mycompany.myapp.repository.NetflixMyListRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.NetflixMyList}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NetflixMyListResource {

    private final Logger log = LoggerFactory.getLogger(NetflixMyListResource.class);

    private static final String ENTITY_NAME = "netflixMyList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetflixMyListRepository netflixMyListRepository;

    public NetflixMyListResource(NetflixMyListRepository netflixMyListRepository) {
        this.netflixMyListRepository = netflixMyListRepository;
    }

    /**
     * {@code POST  /netflix-my-lists} : Create a new netflixMyList.
     *
     * @param netflixMyList the netflixMyList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new netflixMyList, or with status {@code 400 (Bad Request)} if the netflixMyList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/netflix-my-lists")
    public ResponseEntity<NetflixMyList> createNetflixMyList(@Valid @RequestBody NetflixMyList netflixMyList) throws URISyntaxException {
        log.debug("REST request to save NetflixMyList : {}", netflixMyList);
        if (netflixMyList.getId() != null) {
            throw new BadRequestAlertException("A new netflixMyList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetflixMyList result = netflixMyListRepository.save(netflixMyList);
        return ResponseEntity
            .created(new URI("/api/netflix-my-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /netflix-my-lists/:id} : Updates an existing netflixMyList.
     *
     * @param id the id of the netflixMyList to save.
     * @param netflixMyList the netflixMyList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netflixMyList,
     * or with status {@code 400 (Bad Request)} if the netflixMyList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the netflixMyList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/netflix-my-lists/{id}")
    public ResponseEntity<NetflixMyList> updateNetflixMyList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NetflixMyList netflixMyList
    ) throws URISyntaxException {
        log.debug("REST request to update NetflixMyList : {}, {}", id, netflixMyList);
        if (netflixMyList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, netflixMyList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!netflixMyListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NetflixMyList result = netflixMyListRepository.save(netflixMyList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netflixMyList.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /netflix-my-lists/:id} : Partial updates given fields of an existing netflixMyList, field will ignore if it is null
     *
     * @param id the id of the netflixMyList to save.
     * @param netflixMyList the netflixMyList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated netflixMyList,
     * or with status {@code 400 (Bad Request)} if the netflixMyList is not valid,
     * or with status {@code 404 (Not Found)} if the netflixMyList is not found,
     * or with status {@code 500 (Internal Server Error)} if the netflixMyList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/netflix-my-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NetflixMyList> partialUpdateNetflixMyList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NetflixMyList netflixMyList
    ) throws URISyntaxException {
        log.debug("REST request to partial update NetflixMyList partially : {}, {}", id, netflixMyList);
        if (netflixMyList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, netflixMyList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!netflixMyListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NetflixMyList> result = netflixMyListRepository
            .findById(netflixMyList.getId())
            .map(existingNetflixMyList -> {
                if (netflixMyList.getMovieCod() != null) {
                    existingNetflixMyList.setMovieCod(netflixMyList.getMovieCod());
                }

                return existingNetflixMyList;
            })
            .map(netflixMyListRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, netflixMyList.getId().toString())
        );
    }

    /**
     * {@code GET  /netflix-my-lists} : get all the netflixMyLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of netflixMyLists in body.
     */
    @GetMapping("/netflix-my-lists")
    public List<NetflixMyList> getAllNetflixMyLists() {
        log.debug("REST request to get all NetflixMyLists");
        return netflixMyListRepository.findAll();
    }

    /**
     * {@code GET  /netflix-my-lists/:id} : get the "id" netflixMyList.
     *
     * @param id the id of the netflixMyList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the netflixMyList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/netflix-my-lists/{id}")
    public ResponseEntity<NetflixMyList> getNetflixMyList(@PathVariable Long id) {
        log.debug("REST request to get NetflixMyList : {}", id);
        Optional<NetflixMyList> netflixMyList = netflixMyListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(netflixMyList);
    }

    /**
     * {@code DELETE  /netflix-my-lists/:id} : delete the "id" netflixMyList.
     *
     * @param id the id of the netflixMyList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/netflix-my-lists/{id}")
    public ResponseEntity<Void> deleteNetflixMyList(@PathVariable Long id) {
        log.debug("REST request to delete NetflixMyList : {}", id);
        netflixMyListRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
