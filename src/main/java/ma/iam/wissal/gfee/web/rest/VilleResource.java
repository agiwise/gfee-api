package ma.iam.wissal.gfee.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import ma.iam.wissal.gfee.repository.VilleRepository;
import ma.iam.wissal.gfee.service.VilleService;
import ma.iam.wissal.gfee.service.dto.VilleDTO;
import ma.iam.wissal.gfee.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ma.iam.wissal.gfee.domain.Ville}.
 */
@RestController
@RequestMapping("/api")
public class VilleResource {

    private final Logger log = LoggerFactory.getLogger(VilleResource.class);

    private static final String ENTITY_NAME = "ville";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VilleService villeService;

    private final VilleRepository villeRepository;

    public VilleResource(VilleService villeService, VilleRepository villeRepository) {
        this.villeService = villeService;
        this.villeRepository = villeRepository;
    }

    /**
     * {@code POST  /villes} : Create a new ville.
     *
     * @param villeDTO the villeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new villeDTO, or with status {@code 400 (Bad Request)} if the ville has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/villes")
    public ResponseEntity<VilleDTO> createVille(@RequestBody VilleDTO villeDTO) throws URISyntaxException {
        log.debug("REST request to save Ville : {}", villeDTO);
        if (villeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ville cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VilleDTO result = villeService.save(villeDTO);
        return ResponseEntity
            .created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /villes/:id} : Updates an existing ville.
     *
     * @param id the id of the villeDTO to save.
     * @param villeDTO the villeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated villeDTO,
     * or with status {@code 400 (Bad Request)} if the villeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the villeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/villes/{id}")
    public ResponseEntity<VilleDTO> updateVille(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VilleDTO villeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Ville : {}, {}", id, villeDTO);
        if (villeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, villeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VilleDTO result = villeService.save(villeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, villeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /villes/:id} : Partial updates given fields of an existing ville, field will ignore if it is null
     *
     * @param id the id of the villeDTO to save.
     * @param villeDTO the villeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated villeDTO,
     * or with status {@code 400 (Bad Request)} if the villeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the villeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the villeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/villes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VilleDTO> partialUpdateVille(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VilleDTO villeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ville partially : {}, {}", id, villeDTO);
        if (villeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, villeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VilleDTO> result = villeService.partialUpdate(villeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, villeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /villes} : get all the villes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of villes in body.
     */
    @GetMapping("/villes")
    public List<VilleDTO> getAllVilles() {
        log.debug("REST request to get all Villes");
        return villeService.findAll();
    }

    /**
     * {@code GET  /villes/:id} : get the "id" ville.
     *
     * @param id the id of the villeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the villeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/villes/{id}")
    public ResponseEntity<VilleDTO> getVille(@PathVariable Long id) {
        log.debug("REST request to get Ville : {}", id);
        Optional<VilleDTO> villeDTO = villeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(villeDTO);
    }

    /**
     * {@code DELETE  /villes/:id} : delete the "id" ville.
     *
     * @param id the id of the villeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/villes/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        log.debug("REST request to delete Ville : {}", id);
        villeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
