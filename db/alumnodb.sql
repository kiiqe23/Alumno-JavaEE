-- Table: public.alumno

-- DROP TABLE public.alumno;

CREATE TABLE public.alumno
(
    codigoalumno bigint NOT NULL,
    nombrealumno character varying(255) COLLATE pg_catalog."default",
    carreraalumno character varying(255) COLLATE pg_catalog."default",
    calendarioalumno character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT alumno_pkey PRIMARY KEY (codigoalumno)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.alumno
    OWNER to postgres;