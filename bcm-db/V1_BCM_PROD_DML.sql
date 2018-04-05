--------------------------------------------------------
-- DIRETORIA
--------------------------------------------------------
UPDATE DIRETORIA SET NAME = 'Regional RJ+ES' WHERE NAME = 'Regional RJ+ES+MG';
INSERT INTO DIRETORIA (ID, NAME) VALUES (6, 'Regional MG');
INSERT INTO DIRETORIA (ID, NAME) VALUES (7, 'Regional Norte');

UPDATE DIRETORIA SET NAME = 'Regional Sul' WHERE ID = 5;

--------------------------------------------------------
-- UF
--------------------------------------------------------
UPDATE UF SET DIRETORIA_ID = 6 WHERE NAME = 'MG';
UPDATE UF SET DIRETORIA_ID = 7 WHERE NAME = 'MA';
UPDATE UF SET DIRETORIA_ID = 7 WHERE NAME = 'PA';

--------------------------------------------------------
-- CONFIGURATION
--------------------------------------------------------
UPDATE CONFIGURATION SET VALUE = 'Arquivo=Poligono_;fileCorrecoes=Correcao_de_Mercado_;LotFace=Demanda_;LoteFace=Demanda_;idfilePlanilhaNNA=NNA_;fileCEP=CEP_;fileCustosRE=Custos_RE_;fileCustosCompartilhadosRE=Custos_Compartilhados_RE_;fileCustosRI=Custos_RI_;fileCustosCompartilhadosRI=Custos_Compartilhados_RI_;idResultadoFinanceiro=Resultado_Financeiro_;fileDemanda=Demanda_;filePoligono=Poligono_;' WHERE KEY = 'new.name.file';
