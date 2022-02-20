import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const ProjectsMenu = props => (
  <NavDropdown
    icon={['fab', 'react']}
    name={translate('global.menu.projects.main')}
    id="projects-menu"
    data-cy="project"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>
      {/* to avoid warnings when empty */}
      <MenuItem icon={['fab', 'neos']} to="/projects/netflix">
        <Translate contentKey="global.menu.projects.netflix">Netflix</Translate>
      </MenuItem>
    </>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
