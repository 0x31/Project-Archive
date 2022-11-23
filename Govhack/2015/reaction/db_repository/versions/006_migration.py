from sqlalchemy import *
from migrate import *


from migrate.changeset import schema
pre_meta = MetaData()
post_meta = MetaData()
action = Table('action', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('color', String(length=7)),
    Column('name', String(length=120)),
    Column('icon', String(length=120)),
    Column('dataset', String(length=120)),
    Column('act_details', PickleType),
)


def upgrade(migrate_engine):
    # Upgrade operations go here. Don't create your own engine; bind
    # migrate_engine to your metadata
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['action'].columns['icon'].create()


def downgrade(migrate_engine):
    # Operations to reverse the above upgrade go here.
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['action'].columns['icon'].drop()
