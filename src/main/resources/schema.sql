create table if not exists answer_map (
    id bigint not null,
    value char(255),
    answer_content varchar(255) not null,
    primary key (id, answer_content)
);

create table if not exists question (
    id bigint not null,
    question_content varchar(255),
    primary key (id)
);
