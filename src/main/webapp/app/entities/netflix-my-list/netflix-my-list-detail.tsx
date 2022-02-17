import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './netflix-my-list.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NetflixMyListDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const netflixMyListEntity = useAppSelector(state => state.netflixMyList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="netflixMyListDetailsHeading">
          <Translate contentKey="majorreactApp.netflixMyList.detail.title">NetflixMyList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{netflixMyListEntity.id}</dd>
          <dt>
            <span id="movieCod">
              <Translate contentKey="majorreactApp.netflixMyList.movieCod">Movie Cod</Translate>
            </span>
          </dt>
          <dd>{netflixMyListEntity.movieCod}</dd>
          <dt>
            <Translate contentKey="majorreactApp.netflixMyList.user">User</Translate>
          </dt>
          <dd>{netflixMyListEntity.user ? netflixMyListEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/netflix-my-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/netflix-my-list/${netflixMyListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NetflixMyListDetail;
