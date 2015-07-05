from sqlalchemy import *
from migrate import *


from migrate.changeset import schema
pre_meta = MetaData()
post_meta = MetaData()
recipe = Table('recipe', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('action', String(length=140)),
    Column('reaction', String(length=140)),
    Column('user_id', Integer),
    Column('template', String(length=2000)),
)

database = Table('database', post_meta,
    Column('id', Integer, primary_key=True, nullable=False),
    Column('name', String(length=120)),
    Column('icon', String(length=120)),
    Column('url', String(length=120)),
    Column('color', String(length=30)),
    Column('columns', PickleType),
    Column('date', String(length=120)),
    Column('longitude', String(length=120)),
    Column('latitude', String(length=120)),
)


def upgrade(migrate_engine):
    # Upgrade operations go here. Don't create your own engine; bind
    # migrate_engine to your metadata
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['recipe'].columns['template'].create()
    post_meta.tables['database'].columns['date'].create()
    post_meta.tables['database'].columns['latitude'].create()
    post_meta.tables['database'].columns['longitude'].create()


def downgrade(migrate_engine):
    # Operations to reverse the above upgrade go here.
    pre_meta.bind = migrate_engine
    post_meta.bind = migrate_engine
    post_meta.tables['recipe'].columns['template'].drop()
    post_meta.tables['database'].columns['date'].drop()
    post_meta.tables['database'].columns['latitude'].drop()
    post_meta.tables['database'].columns['longitude'].drop()
