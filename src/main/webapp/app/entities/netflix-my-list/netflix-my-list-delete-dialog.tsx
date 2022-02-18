import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './netflix-my-list.reducer';

export const NetflixMyListDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const [loadModal, setLoadModal] = useState(false);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
    setLoadModal(true);
  }, []);

  const netflixMyListEntity = useAppSelector(state => state.netflixMyList.entity);
  const updateSuccess = useAppSelector(state => state.netflixMyList.updateSuccess);

  const handleClose = () => {
    props.history.push('/netflix-my-list');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(netflixMyListEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="netflixMyListDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="majorreactApp.netflixMyList.delete.question">
        <Translate contentKey="majorreactApp.netflixMyList.delete.question" interpolate={{ id: netflixMyListEntity.id }}>
          Are you sure you want to delete this NetflixMyList?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-netflixMyList" data-cy="entityConfirmDeleteButton" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default NetflixMyListDeleteDialog;
