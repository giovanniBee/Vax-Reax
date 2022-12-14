PGDMP                          z           Vaccini    14.5    14.5 N    X           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            Y           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            Z           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            [           1262    40991    Vaccini    DATABASE     e   CREATE DATABASE "Vaccini" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "Vaccini";
                postgres    false            ?            1259    40992    FattoreDiRischio    TABLE     ?   CREATE TABLE public."FattoreDiRischio" (
    "Nome" character varying NOT NULL,
    "Descrizione" character varying,
    "LivelloDiRischio" integer NOT NULL
);
 &   DROP TABLE public."FattoreDiRischio";
       public         heap    postgres    false            \           0    0    TABLE "FattoreDiRischio"    COMMENT     Y   COMMENT ON TABLE public."FattoreDiRischio" IS 'Fattori di rischio relativi ai pazienti';
          public          postgres    false    209            ?            1259    40997    Paziente    TABLE     ?   CREATE TABLE public."Paziente" (
    "Codice" integer NOT NULL,
    "Anno" integer NOT NULL,
    "Residenza" character varying,
    "Professione" character varying
);
    DROP TABLE public."Paziente";
       public         heap    postgres    false            ?            1259    41002    Paziente_Codice_seq    SEQUENCE     ?   CREATE SEQUENCE public."Paziente_Codice_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."Paziente_Codice_seq";
       public          postgres    false    210            ]           0    0    Paziente_Codice_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public."Paziente_Codice_seq" OWNED BY public."Paziente"."Codice";
          public          postgres    false    211            ?            1259    41003    Paziente_FattoreDiRischio    TABLE     ?   CREATE TABLE public."Paziente_FattoreDiRischio" (
    "Paziente_Codice" integer NOT NULL,
    "FattoreDiRischio_Nome" character varying NOT NULL
);
 /   DROP TABLE public."Paziente_FattoreDiRischio";
       public         heap    postgres    false            ?            1259    41008 -   Paziente_FattoreDiRischio_Paziente_Codice_seq    SEQUENCE     ?   CREATE SEQUENCE public."Paziente_FattoreDiRischio_Paziente_Codice_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 F   DROP SEQUENCE public."Paziente_FattoreDiRischio_Paziente_Codice_seq";
       public          postgres    false    212            ^           0    0 -   Paziente_FattoreDiRischio_Paziente_Codice_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Paziente_FattoreDiRischio_Paziente_Codice_seq" OWNED BY public."Paziente_FattoreDiRischio"."Paziente_Codice";
          public          postgres    false    213            ?            1259    41009    Paziente_FattoreRischio    TABLE     ?   CREATE TABLE public."Paziente_FattoreRischio" (
    "codicePaziente" integer NOT NULL,
    "nomeFattore" character varying(30) NOT NULL
);
 -   DROP TABLE public."Paziente_FattoreRischio";
       public         heap    postgres    false            ?            1259    41012    PropostaFarmacologo    TABLE     ?   CREATE TABLE public."PropostaFarmacologo" (
    "Codice" integer NOT NULL,
    "Nome" character varying,
    "UserFarmacologo" character varying,
    "Data" date DEFAULT CURRENT_DATE,
    "Descrizione" character varying(300)
);
 )   DROP TABLE public."PropostaFarmacologo";
       public         heap    postgres    false            ?            1259    41018    PropostaFarmacologo_Codice_seq    SEQUENCE     ?   CREATE SEQUENCE public."PropostaFarmacologo_Codice_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE public."PropostaFarmacologo_Codice_seq";
       public          postgres    false    215            _           0    0    PropostaFarmacologo_Codice_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public."PropostaFarmacologo_Codice_seq" OWNED BY public."PropostaFarmacologo"."Codice";
          public          postgres    false    216            ?            1259    41019    ReazioneAvversa    TABLE     Q   CREATE TABLE public."ReazioneAvversa" (
    "Nome" character varying NOT NULL
);
 %   DROP TABLE public."ReazioneAvversa";
       public         heap    postgres    false            ?            1259    41024    Sede    TABLE     `   CREATE TABLE public."Sede" (
    "Nome" character varying,
    "Provincia" character varying
);
    DROP TABLE public."Sede";
       public         heap    postgres    false            ?            1259    41029    Segnalazione    TABLE     ?  CREATE TABLE public."Segnalazione" (
    "Codice" integer NOT NULL,
    "DataReazione" date DEFAULT CURRENT_DATE NOT NULL,
    "ReazioneAvversa" character varying NOT NULL,
    "CodicePaziente" integer NOT NULL,
    "UserMedico" character varying NOT NULL,
    "DataReport" date,
    "Gravita" integer,
    "Descrizione" character varying,
    CONSTRAINT "checkOnReportDate" CHECK (("DataReport" >= "DataReazione"))
);
 "   DROP TABLE public."Segnalazione";
       public         heap    postgres    false            `           0    0    TABLE "Segnalazione"    COMMENT     j   COMMENT ON TABLE public."Segnalazione" IS 'Segnalazioni fatte dal medico di reazioni avverse al vaccino';
          public          postgres    false    219            ?            1259    41035    Utente    TABLE     ?   CREATE TABLE public."Utente" (
    "Username" character varying NOT NULL,
    "Password" character varying NOT NULL,
    "Professione" smallint,
    "Sale" character varying
);
    DROP TABLE public."Utente";
       public         heap    postgres    false            ?            1259    41040    Vaccinazione    TABLE     ?   CREATE TABLE public."Vaccinazione" (
    "Nome" character varying NOT NULL,
    "Tipo" character varying NOT NULL,
    "CodicePaziente" integer NOT NULL,
    "Sede" character varying,
    "Data" date
);
 "   DROP TABLE public."Vaccinazione";
       public         heap    postgres    false            ?            1259    41045    Vaccinazione_Segnalazione    TABLE       CREATE TABLE public."Vaccinazione_Segnalazione" (
    "Vaccinazione_Nome" character varying NOT NULL,
    "Segnalazione_Codice" integer DEFAULT (+ 1) NOT NULL,
    "Vaccinazione_Tipo" character varying NOT NULL,
    "Vaccinazione_Codice" integer NOT NULL
);
 /   DROP TABLE public."Vaccinazione_Segnalazione";
       public         heap    postgres    false            ?            1259    41051    codice_sequence    SEQUENCE     w   CREATE SEQUENCE public.codice_sequence
    START WITH 7
    INCREMENT BY 1
    MINVALUE 7
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.codice_sequence;
       public          postgres    false    219            a           0    0    codice_sequence    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.codice_sequence OWNED BY public."Segnalazione"."Codice";
          public          postgres    false    223            ?            1259    41052    new_sequence    SEQUENCE     t   CREATE SEQUENCE public.new_sequence
    START WITH 3
    INCREMENT BY 1
    MINVALUE 3
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.new_sequence;
       public          postgres    false    215            b           0    0    new_sequence    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.new_sequence OWNED BY public."PropostaFarmacologo"."Codice";
          public          postgres    false    224            ?            1259    41053    test_id_seq    SEQUENCE     s   CREATE SEQUENCE public.test_id_seq
    START WITH 3
    INCREMENT BY 1
    MINVALUE 3
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.test_id_seq;
       public          postgres    false            ?            1259    41054    test_id_sequence    SEQUENCE     x   CREATE SEQUENCE public.test_id_sequence
    START WITH 3
    INCREMENT BY 1
    MINVALUE 3
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.test_id_sequence;
       public          postgres    false    219            c           0    0    test_id_sequence    SEQUENCE OWNED BY     P   ALTER SEQUENCE public.test_id_sequence OWNED BY public."Segnalazione"."Codice";
          public          postgres    false    226            ?           2604    41055    Paziente Codice    DEFAULT     x   ALTER TABLE ONLY public."Paziente" ALTER COLUMN "Codice" SET DEFAULT nextval('public."Paziente_Codice_seq"'::regclass);
 B   ALTER TABLE public."Paziente" ALTER COLUMN "Codice" DROP DEFAULT;
       public          postgres    false    211    210            ?           2604    41056 )   Paziente_FattoreDiRischio Paziente_Codice    DEFAULT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" ALTER COLUMN "Paziente_Codice" SET DEFAULT nextval('public."Paziente_FattoreDiRischio_Paziente_Codice_seq"'::regclass);
 \   ALTER TABLE public."Paziente_FattoreDiRischio" ALTER COLUMN "Paziente_Codice" DROP DEFAULT;
       public          postgres    false    213    212            ?           2604    41057    PropostaFarmacologo Codice    DEFAULT     z   ALTER TABLE ONLY public."PropostaFarmacologo" ALTER COLUMN "Codice" SET DEFAULT nextval('public.new_sequence'::regclass);
 M   ALTER TABLE public."PropostaFarmacologo" ALTER COLUMN "Codice" DROP DEFAULT;
       public          postgres    false    224    215            ?           2604    41058    Segnalazione Codice    DEFAULT     v   ALTER TABLE ONLY public."Segnalazione" ALTER COLUMN "Codice" SET DEFAULT nextval('public.codice_sequence'::regclass);
 F   ALTER TABLE public."Segnalazione" ALTER COLUMN "Codice" DROP DEFAULT;
       public          postgres    false    223    219            D          0    40992    FattoreDiRischio 
   TABLE DATA           W   COPY public."FattoreDiRischio" ("Nome", "Descrizione", "LivelloDiRischio") FROM stdin;
    public          postgres    false    209   ?e       E          0    40997    Paziente 
   TABLE DATA           R   COPY public."Paziente" ("Codice", "Anno", "Residenza", "Professione") FROM stdin;
    public          postgres    false    210   sf       G          0    41003    Paziente_FattoreDiRischio 
   TABLE DATA           a   COPY public."Paziente_FattoreDiRischio" ("Paziente_Codice", "FattoreDiRischio_Nome") FROM stdin;
    public          postgres    false    212   
g       I          0    41009    Paziente_FattoreRischio 
   TABLE DATA           T   COPY public."Paziente_FattoreRischio" ("codicePaziente", "nomeFattore") FROM stdin;
    public          postgres    false    214   6g       J          0    41012    PropostaFarmacologo 
   TABLE DATA           k   COPY public."PropostaFarmacologo" ("Codice", "Nome", "UserFarmacologo", "Data", "Descrizione") FROM stdin;
    public          postgres    false    215   xg       L          0    41019    ReazioneAvversa 
   TABLE DATA           3   COPY public."ReazioneAvversa" ("Nome") FROM stdin;
    public          postgres    false    217   Kh       M          0    41024    Sede 
   TABLE DATA           5   COPY public."Sede" ("Nome", "Provincia") FROM stdin;
    public          postgres    false    218   ?h       N          0    41029    Segnalazione 
   TABLE DATA           ?   COPY public."Segnalazione" ("Codice", "DataReazione", "ReazioneAvversa", "CodicePaziente", "UserMedico", "DataReport", "Gravita", "Descrizione") FROM stdin;
    public          postgres    false    219   Ji       O          0    41035    Utente 
   TABLE DATA           Q   COPY public."Utente" ("Username", "Password", "Professione", "Sale") FROM stdin;
    public          postgres    false    220   ?k       P          0    41040    Vaccinazione 
   TABLE DATA           Z   COPY public."Vaccinazione" ("Nome", "Tipo", "CodicePaziente", "Sede", "Data") FROM stdin;
    public          postgres    false    221   m       Q          0    41045    Vaccinazione_Segnalazione 
   TABLE DATA           ?   COPY public."Vaccinazione_Segnalazione" ("Vaccinazione_Nome", "Segnalazione_Codice", "Vaccinazione_Tipo", "Vaccinazione_Codice") FROM stdin;
    public          postgres    false    222   ?m       d           0    0    Paziente_Codice_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."Paziente_Codice_seq"', 1, false);
          public          postgres    false    211            e           0    0 -   Paziente_FattoreDiRischio_Paziente_Codice_seq    SEQUENCE SET     ^   SELECT pg_catalog.setval('public."Paziente_FattoreDiRischio_Paziente_Codice_seq"', 1, false);
          public          postgres    false    213            f           0    0    PropostaFarmacologo_Codice_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public."PropostaFarmacologo_Codice_seq"', 1, true);
          public          postgres    false    216            g           0    0    codice_sequence    SEQUENCE SET     >   SELECT pg_catalog.setval('public.codice_sequence', 44, true);
          public          postgres    false    223            h           0    0    new_sequence    SEQUENCE SET     :   SELECT pg_catalog.setval('public.new_sequence', 6, true);
          public          postgres    false    224            i           0    0    test_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.test_id_seq', 3, false);
          public          postgres    false    225            j           0    0    test_id_sequence    SEQUENCE SET     >   SELECT pg_catalog.setval('public.test_id_sequence', 3, true);
          public          postgres    false    226            ?           2606    41060    Segnalazione Codice 
   CONSTRAINT     [   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "Codice" PRIMARY KEY ("Codice");
 A   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "Codice";
       public            postgres    false    219            ?           2606    41062 &   FattoreDiRischio FattoreDiRischio_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public."FattoreDiRischio"
    ADD CONSTRAINT "FattoreDiRischio_pkey" PRIMARY KEY ("Nome");
 T   ALTER TABLE ONLY public."FattoreDiRischio" DROP CONSTRAINT "FattoreDiRischio_pkey";
       public            postgres    false    209            ?           2606    41064    Vaccinazione PK 
   CONSTRAINT     o   ALTER TABLE ONLY public."Vaccinazione"
    ADD CONSTRAINT "PK" PRIMARY KEY ("Nome", "Tipo", "CodicePaziente");
 =   ALTER TABLE ONLY public."Vaccinazione" DROP CONSTRAINT "PK";
       public            postgres    false    221    221    221            ?           2606    41066    PropostaFarmacologo PKEY 
   CONSTRAINT     `   ALTER TABLE ONLY public."PropostaFarmacologo"
    ADD CONSTRAINT "PKEY" PRIMARY KEY ("Codice");
 F   ALTER TABLE ONLY public."PropostaFarmacologo" DROP CONSTRAINT "PKEY";
       public            postgres    false    215            ?           2606    41068 9   Vaccinazione_Segnalazione PK_NM_Vaccinazione_Segnalazione 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Vaccinazione_Segnalazione"
    ADD CONSTRAINT "PK_NM_Vaccinazione_Segnalazione" PRIMARY KEY ("Vaccinazione_Nome", "Segnalazione_Codice", "Vaccinazione_Tipo", "Vaccinazione_Codice");
 g   ALTER TABLE ONLY public."Vaccinazione_Segnalazione" DROP CONSTRAINT "PK_NM_Vaccinazione_Segnalazione";
       public            postgres    false    222    222    222    222            ?           2606    41070 2   Paziente_FattoreRischio PK_Paziente_FattoreRischio 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreRischio"
    ADD CONSTRAINT "PK_Paziente_FattoreRischio" PRIMARY KEY ("codicePaziente", "nomeFattore");
 `   ALTER TABLE ONLY public."Paziente_FattoreRischio" DROP CONSTRAINT "PK_Paziente_FattoreRischio";
       public            postgres    false    214    214            ?           2606    41072 Y   Paziente_FattoreDiRischio Paziente_FattoreDiRischio_FattoreDiRischio_Nome_FattoreDiRi_key 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT "Paziente_FattoreDiRischio_FattoreDiRischio_Nome_FattoreDiRi_key" UNIQUE ("FattoreDiRischio_Nome") INCLUDE ("FattoreDiRischio_Nome");
 ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT "Paziente_FattoreDiRischio_FattoreDiRischio_Nome_FattoreDiRi_key";
       public            postgres    false    212            ?           2606    41074 X   Paziente_FattoreDiRischio Paziente_FattoreDiRischio_Paziente_Codice_Paziente_Codice1_key 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT "Paziente_FattoreDiRischio_Paziente_Codice_Paziente_Codice1_key" UNIQUE ("Paziente_Codice") INCLUDE ("Paziente_Codice");
 ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT "Paziente_FattoreDiRischio_Paziente_Codice_Paziente_Codice1_key";
       public            postgres    false    212            ?           2606    41076 8   Paziente_FattoreDiRischio Paziente_FattoreDiRischio_pkey 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT "Paziente_FattoreDiRischio_pkey" PRIMARY KEY ("Paziente_Codice") INCLUDE ("FattoreDiRischio_Nome");
 f   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT "Paziente_FattoreDiRischio_pkey";
       public            postgres    false    212    212            ?           2606    41078    Paziente Paziente_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Paziente"
    ADD CONSTRAINT "Paziente_pkey" PRIMARY KEY ("Codice");
 D   ALTER TABLE ONLY public."Paziente" DROP CONSTRAINT "Paziente_pkey";
       public            postgres    false    210            ?           2606    41080 $   ReazioneAvversa ReazioneAvversa_pkey 
   CONSTRAINT     j   ALTER TABLE ONLY public."ReazioneAvversa"
    ADD CONSTRAINT "ReazioneAvversa_pkey" PRIMARY KEY ("Nome");
 R   ALTER TABLE ONLY public."ReazioneAvversa" DROP CONSTRAINT "ReazioneAvversa_pkey";
       public            postgres    false    217            ?           2606    41082    Utente Utente_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public."Utente"
    ADD CONSTRAINT "Utente_pkey" PRIMARY KEY ("Username");
 @   ALTER TABLE ONLY public."Utente" DROP CONSTRAINT "Utente_pkey";
       public            postgres    false    220            ?           2606    41084 !   Paziente_FattoreDiRischio uniq_cp 
   CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT uniq_cp UNIQUE ("Paziente_Codice", "FattoreDiRischio_Nome");
 M   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT uniq_cp;
       public            postgres    false    212    212            ?           2606    41085    Vaccinazione CodicePaziente    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Vaccinazione"
    ADD CONSTRAINT "CodicePaziente" FOREIGN KEY ("CodicePaziente") REFERENCES public."Paziente"("Codice");
 I   ALTER TABLE ONLY public."Vaccinazione" DROP CONSTRAINT "CodicePaziente";
       public          postgres    false    3221    210    221            ?           2606    41090 0   Vaccinazione_Segnalazione FK_Segnalazione_Codice    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Vaccinazione_Segnalazione"
    ADD CONSTRAINT "FK_Segnalazione_Codice" FOREIGN KEY ("Segnalazione_Codice") REFERENCES public."Segnalazione"("Codice");
 ^   ALTER TABLE ONLY public."Vaccinazione_Segnalazione" DROP CONSTRAINT "FK_Segnalazione_Codice";
       public          postgres    false    3237    219    222            ?           2606    41095 -   Vaccinazione_Segnalazione FK_Vaccinazione_Tpo    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Vaccinazione_Segnalazione"
    ADD CONSTRAINT "FK_Vaccinazione_Tpo" FOREIGN KEY ("Vaccinazione_Tipo", "Vaccinazione_Nome", "Vaccinazione_Codice") REFERENCES public."Vaccinazione"("Tipo", "Nome", "CodicePaziente");
 [   ALTER TABLE ONLY public."Vaccinazione_Segnalazione" DROP CONSTRAINT "FK_Vaccinazione_Tpo";
       public          postgres    false    221    3241    222    222    222    221    221            ?           2606    41100    Segnalazione Paziente    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "Paziente" FOREIGN KEY ("CodicePaziente") REFERENCES public."Paziente"("Codice");
 C   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "Paziente";
       public          postgres    false    210    219    3221            ?           2606    41105 N   Paziente_FattoreDiRischio Paziente_FattoreDiRischio_FattoreDiRischio_Nome_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT "Paziente_FattoreDiRischio_FattoreDiRischio_Nome_fkey" FOREIGN KEY ("FattoreDiRischio_Nome") REFERENCES public."FattoreDiRischio"("Nome");
 |   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT "Paziente_FattoreDiRischio_FattoreDiRischio_Nome_fkey";
       public          postgres    false    212    209    3219            ?           2606    41110 H   Paziente_FattoreDiRischio Paziente_FattoreDiRischio_Paziente_Codice_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreDiRischio"
    ADD CONSTRAINT "Paziente_FattoreDiRischio_Paziente_Codice_fkey" FOREIGN KEY ("Paziente_Codice") REFERENCES public."Paziente"("Codice");
 v   ALTER TABLE ONLY public."Paziente_FattoreDiRischio" DROP CONSTRAINT "Paziente_FattoreDiRischio_Paziente_Codice_fkey";
       public          postgres    false    3221    212    210            ?           2606    41115 <   PropostaFarmacologo PropostaFarmacologo_UserFarmacologo_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public."PropostaFarmacologo"
    ADD CONSTRAINT "PropostaFarmacologo_UserFarmacologo_fkey" FOREIGN KEY ("UserFarmacologo") REFERENCES public."Utente"("Username");
 j   ALTER TABLE ONLY public."PropostaFarmacologo" DROP CONSTRAINT "PropostaFarmacologo_UserFarmacologo_fkey";
       public          postgres    false    215    220    3239            ?           2606    41120 -   Segnalazione Segnalazione_CodicePaziente_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "Segnalazione_CodicePaziente_fkey" FOREIGN KEY ("CodicePaziente") REFERENCES public."Paziente"("Codice");
 [   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "Segnalazione_CodicePaziente_fkey";
       public          postgres    false    219    210    3221            ?           2606    41125 )   Segnalazione Segnalazione_UserMedico_fkey    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "Segnalazione_UserMedico_fkey" FOREIGN KEY ("UserMedico") REFERENCES public."Utente"("Username");
 W   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "Segnalazione_UserMedico_fkey";
       public          postgres    false    3239    219    220            ?           2606    41130    Segnalazione UserMedico    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "UserMedico" FOREIGN KEY ("UserMedico") REFERENCES public."Utente"("Username");
 E   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "UserMedico";
       public          postgres    false    3239    220    219            ?           2606    41135 #   Paziente_FattoreRischio nomeFattore    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreRischio"
    ADD CONSTRAINT "nomeFattore" FOREIGN KEY ("nomeFattore") REFERENCES public."FattoreDiRischio"("Nome") NOT VALID;
 Q   ALTER TABLE ONLY public."Paziente_FattoreRischio" DROP CONSTRAINT "nomeFattore";
       public          postgres    false    3219    209    214            ?           2606    41140     Paziente_FattoreRischio paziente    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Paziente_FattoreRischio"
    ADD CONSTRAINT paziente FOREIGN KEY ("codicePaziente") REFERENCES public."Paziente"("Codice");
 L   ALTER TABLE ONLY public."Paziente_FattoreRischio" DROP CONSTRAINT paziente;
       public          postgres    false    214    210    3221            ?           2606    41145    Segnalazione reazioniAvverse    FK CONSTRAINT     ?   ALTER TABLE ONLY public."Segnalazione"
    ADD CONSTRAINT "reazioniAvverse" FOREIGN KEY ("ReazioneAvversa") REFERENCES public."ReazioneAvversa"("Nome");
 J   ALTER TABLE ONLY public."Segnalazione" DROP CONSTRAINT "reazioniAvverse";
       public          postgres    false    217    219    3235            D   ?   x?]???@E??)<?R
?8??,]?'?P0%{d1L??}???KOƾ?SW?ዩ:???`?.02?<[#???g,?????D???VДF?&?K?B3?)8??1????G	J??9???z??]#u??R)]?lM?????? ?!?v9?/n5O?      E   ?   x?-??
?0E?3_?/??Z?[ADP
??f??8?<\??M??sϹt?8?M?E'*p??O%͌}?;??kx?C"8x?9?CR&\7?oܳq?͙>!???_l??vY?={֙?Ô???????'?Y?)??x_!?D?0?      G      x?3??,H-*I?+???K?????? D??      I   2   x?3??,H-*I?+???K?2??OJ-?,9??˘ӭ47?$?(?+F??? 3?z      J   ?   x?u?1?0E???@Q?
?!P%V\j??zz"hAB?Z???m?:?(?? ?6
??ɢ??*?????,?q??j???{????'I???+?Gr??Ba?ܡ?=?1?g??>tx?AR???wv???W?J ??#??:?6?`ԥ??	j?&H?#L??ţ{~?z?v?L??C????5????]=??q??~ ?iu?      L   a   x??1?0???????n?2?|??j?ڒ?,?:??ݔB<b???~h	?/?]?d?!??!Xw/??U3?8f?K?o????b??/?? ? 7?#?      M   ~   x?E?K
?PE??*??@?UKGN.?????U??????N??;_????p??4!6???Z
???]??8?i???wR	p?\??7?m?F???\4?I?޶?F0h???x?.??1??e?4?M?ہ???=?      N   ?  x??V??? =?W?Y?c???V{?Vm?S.??Z$?X??_?c??????I???,??x3oLK[%Ŋ??I	c?@?^?m??ܭ$?d?b	zԍ??(r\???!?xS????
2????+?h#?j??3H'????????g%9???^?tN???#?u?x???????4N????z?? =
LӐb?s?eDaa/ ????(5?ٴ?;?7Q?FV??2?V?@J\??Hw?X?Ψ?T??#?j???`??pZ?H????G?c??Bj?????L??kfM)銠?]Ru??L?܋?0???c~???F?&?Q?[Egؑr??ۺd?v!??&???????3??)uo????>??W?????????,??{Ɍ??????c????-??Ştggq?8n?R??Q;?[?uUW?G-?,#??`qY??@?W	??46???VB?`?z>?????:oED?h?y4)z1???(fz?/U??"f?_I9@+K??:?e?o???M?f?ڪr???2?
??G?F??3N׋?????71??N???.?^???i(?k??95?????1?4????r??YB?\ ?)Glx6??ڳ?A????Q?0????E???????7????hLR      O   3  x??KkA???q???n;K6???c??X?j	?2,????SA]>??,S %?3c`Vn??Qz ?N?ze?=?y?&=?d!?>???^6??~{??ͯ????>e??Һ???&?ڡ?\?#?D?\?%????S?F?J1& ?<??????ծ???h?^lP??????	2?1??R?Z?u???s?KŒLF?@š
Qh???A<?????8~???S?6??>-??)#???*q??)? "????*gr]Kf?b9d,??q?-._?????ǅe????m?v???4??2????oj      P   ?   x?e??
?0?s?}?I[?сބΐ??u???)??7S6IO???ˊ[?&<<w<?*'Ͼ#?{d]??Epƹ??O??m?N?O??"S??癱?p*S?t???<??[UeC?O???D!`?????g?d?!?xű??jk?XV0??_??b?Y???????Æ|B]?.???????1Ed?y??z?UV?      Q   ?   x?e?=
?0?Y:L?%;?cPt?bR?8??9}]?!<???????D??w?<??)???q??N6???R?dڼiOv@??)HE:d?l?x???g???}Y??Bc?4?Y1rר.?Q??Ќv@???N?-7f???]?     