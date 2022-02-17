import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './netflix-my-list.reducer';
import { INetflixMyList } from 'app/shared/model/netflix-my-list.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const NetflixMyList = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const netflixMyListList = useAppSelector(state => state.netflixMyList.entities);
  const loading = useAppSelector(state => state.netflixMyList.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="netflix-my-list-heading" data-cy="NetflixMyListHeading">
        <Translate contentKey="majorreactApp.netflixMyList.home.title">Netflix My Lists</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="majorreactApp.netflixMyList.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="majorreactApp.netflixMyList.home.createLabel">Create new Netflix My List</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {netflixMyListList && netflixMyListList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="majorreactApp.netflixMyList.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="majorreactApp.netflixMyList.movieCod">Movie Cod</Translate>
                </th>
                <th>
                  <Translate contentKey="majorreactApp.netflixMyList.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {netflixMyListList.map((netflixMyList, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${netflixMyList.id}`} color="link" size="sm">
                      {netflixMyList.id}
                    </Button>
                  </td>
                  <td>{netflixMyList.movieCod}</td>
                  <td>{netflixMyList.user ? netflixMyList.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${netflixMyList.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${netflixMyList.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${netflixMyList.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="majorreactApp.netflixMyList.home.notFound">No Netflix My Lists found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default NetflixMyList;
