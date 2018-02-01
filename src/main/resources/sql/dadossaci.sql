DROP TABLE IF EXISTS TCL;
CREATE TEMPORARY TABLE TCL
(PRIMARY KEY (CLNO))
SELECT no AS CLNO, 
    SUBSTRING_INDEX(name,'/',1)  AS  GRUPO,
    SUBSTRING_INDEX(
    SUBSTRING_INDEX(name,'/',2),'/',-1) AS DEPARTAMENTO,
    SUBSTRING_INDEX(name,'/',-1)  AS  SECAO
 FROM sqldados.cl;

UPDATE coletor.cl SET VERSION = -1;

INSERT INTO coletor.cl(CLNO, DEPARTAMENTO, GRUPO, SECAO, VERSION)
SELECT TCL.CLNO, TCL.DEPARTAMENTO, TCL.GRUPO, TCL.SECAO, 1
FROM TCL
ON DUPLICATE KEY UPDATE DEPARTAMENTO = VALUES(DEPARTAMENTO),
                        GRUPO        = VALUES(GRUPO),
                        SECAO       = VALUES(SECAO),
                        VERSION      = 1;

DELETE FROM coletor.cl WHERE VERSION = -1;

/*****************************************************************************/

DROP TABLE IF EXISTS TLJ;
CREATE TEMPORARY TABLE TLJ
(PRIMARY KEY (STORENO))
SELECT no as STORENO, sname as SIGLA, otherName as NOME, addr AS ENDERECO
FROM sqldados.store;

UPDATE coletor.loja SET VERSION = -1;

INSERT INTO coletor.loja(ENDERECO, NOME, SIGLA, STORENO, VERSION)
SELECT ENDERECO, NOME, SIGLA, STORENO, 1 AS VERSION FROM TLJ
ON DUPLICATE KEY UPDATE ENDERECO = VALUES(ENDERECO), 
                        NOME     = VALUES(NOME),
                        SIGLA    = VALUES(SIGLA),
                        VERSION  = 1;


DELETE FROM coletor.loja WHERE VERSION = -1;

/*****************************************************************************/

DROP TABLE IF EXISTS TVD;
CREATE TEMPORARY TABLE TVD
(PRIMARY KEY (CODIGO))
select no as CODIGO, name AS RAZAO, sname AS FANTAZIA
FROM sqldados.vend;

UPDATE coletor.fornecedor SET VERSION = -1;

INSERT INTO coletor.fornecedor(CODIGO, FANTAZIA, RAZAO, VERSION) 
SELECT CODIGO, FANTAZIA, RAZAO, 1 AS VERSION
FROM TVD
ON DUPLICATE KEY UPDATE FANTAZIA = VALUES(FANTAZIA), 
                        RAZAO    = VALUES(RAZAO),
                        VERSION  = 1;

DELETE FROM coletor.fornecedor WHERE VERSION = -1;

/*****************************************************************************/

DROP TABLE IF EXISTS TPR;
CREATE TEMPORARY TABLE TPR
(PRIMARY KEY (BARCODE))
SELECT P.no AS CODIGO, IFNULL(B.grade, '') AS GRADE, TRIM(IFNULL(B.barcode, P.barcode)) AS BARCODE,
  TRIM(MID(P.name, 1, 38)) AS DESCRICAO, P.bits & POW(2, 13) != 0 AS USOCONSUMO, 
  dereg & POW(2, 2) != 0 AS FORALINHA, COUNT(*) > 1 AS DUPLICADO,
  mfno AS VENDNO, P.clno AS CLNO,
  F.ID AS FORNECEDOR_ID, C.ID AS CL_ID
FROM sqldados.prd AS P
  LEFT JOIN sqldados.prdbar AS B
    ON P.no = B.prdno
  LEFT JOIN coletor.fornecedor AS F
    ON F.CODIGO = P.mfno
  LEFT JOIN coletor.cl AS C
    ON C.CLNO = P.clno
GROUP BY BARCODE
HAVING CL_ID is not null AND FORNECEDOR_ID is not null;

UPDATE coletor.produto SET VERSION = -1;

INSERT IGNORE INTO coletor.produto(BARCODE, CODIGO, DESCRICAO, DUPLICADO, FORALINHA, GRADE, 
                     USOCONSUMO, VERSION, FORNECEDOR_ID, CL_ID)
SELECT BARCODE, TPR.CODIGO, DESCRICAO, DUPLICADO, FORALINHA, GRADE, USOCONSUMO, 
  1 AS VERSION, FORNECEDOR_ID, CL_ID
FROM TPR
ON DUPLICATE KEY UPDATE CODIGO        = VALUES(CODIGO), 
                        DESCRICAO     = VALUES(DESCRICAO),
                        DUPLICADO     = VALUES(DUPLICADO),
                        FORALINHA     = VALUES(FORALINHA),
                        GRADE         = VALUES(GRADE),
                        USOCONSUMO    = VALUES(USOCONSUMO),
                        FORNECEDOR_ID = VALUES(FORNECEDOR_ID),
                        CL_ID         = VALUES(CL_ID),
                        VERSION       = 1;

DELETE FROM coletor.produto WHERE VERSION = -1;

/*****************************************************************************/

DO @FRASE:='&%#!@$*+';

DROP TABLE IF EXISTS TUS;
CREATE TEMPORARY TABLE TUS
(PRIMARY KEY (MATRICULA))
SELECT id as MATRICULA, name as NOME, if(sname = '', MID(name, 1, 30), MID(sname, 1, 30)) AS APELIDO,
 CONCAT(
   CHAR(ASCII(mid(pswd, 1,1 )) + 33 - ((0 % 3) * 3) - ASCII(mid(@FRASE, 1,1 ))),
   CHAR(ASCII(mid(pswd, 2,1 )) + 33 - ((1 % 3) * 3) - ASCII(mid(@FRASE, 2,1 ))),
   CHAR(ASCII(mid(pswd, 3,1 )) + 33 - ((2 % 3) * 3) - ASCII(mid(@FRASE, 3,1 ))),
   CHAR(ASCII(mid(pswd, 4,1 )) + 33 - ((3 % 3) * 3) - ASCII(mid(@FRASE, 4,1 ))),
   CHAR(ASCII(mid(pswd, 5,1 )) + 33 - ((4 % 3) * 3) - ASCII(mid(@FRASE, 5,1 ))),
   CHAR(ASCII(mid(pswd, 6,1 )) + 33 - ((5 % 3) * 3) - ASCII(mid(@FRASE, 6,1 ))),
   CHAR(ASCII(mid(pswd, 7,1 )) + 33 - ((6 % 3) * 3) - ASCII(mid(@FRASE, 7,1 ))),
   CHAR(ASCII(mid(pswd, 8,1 )) + 33 - ((7 % 3) * 3) - ASCII(mid(@FRASE, 8,1 )))
 ) as senha
from sqldados.emp
where dateDemissao = 0
  AND id <> '.'
  AND id regexp '^[0-9]+$';

UPDATE coletor.usuario SET VERSION = -1;

INSERT INTO coletor.usuario(MATRICULA, NOME, APELIDO, SENHA, VERSION)
SELECT MATRICULA, NOME, APELIDO, SENHA, 1 AS VERSION
FROM TUS
ON DUPLICATE KEY UPDATE NOME        = VALUES(NOME), 
                        APELIDO     = VALUES(APELIDO),
                        SENHA       = VALUES(SENHA),
                        VERSION     = 1;

DELETE FROM coletor.usuario WHERE VERSION = -1;

/************************************************************/

DELETE FROM estoque
where inventario_id IN (select id from inventario where statusInventario = 'Aberto');

DROP TABLE IF EXISTS T;
CREATE TEMPORARY TABLE T
(PRIMARY KEY(id))
SELECT I.id, LPAD(ifnull(clno, ''), 6, '0') AS clno, 
  ifnull(I.fornecedor_id, '') as fornecedor_id, storeno 
FROM inventario AS I
  INNER JOIN loja AS L
    ON L.id = I.loja_id
  LEFT join cl AS C
    ON I.cl_id = C.id
WHERE statusInventario = 'Aberto';

DROP TABLE IF EXISTS T2;
CREATE TEMPORARY TABLE T2
SELECT T.id AS inventario_id, cl.id AS cl_id, fornecedor_id, storeno 
  FROM T
    INNER JOIN cl
      ON cl.clno like  IF(T.clno LIKE '000000', cl.clno,
                        IF(T.clno LIKE '%0000', CONCAT(MID(T.clno, 1, 2), '%'),
                        IF(T.clno LIKE '%00', CONCAT(MID(T.clno, 1, 4), '%'),
                          cl.clno))); 

DROP TABLE IF EXISTS T3;
CREATE TEMPORARY TABLE T3
(PRIMARY KEY(inventario_id, produto_id))
select inventario_id, id as produto_id, S.qtty_varejo/1000 AS quant
from T2
  INNER JOIN produto AS P
    ON  (T2.cl_id = P.cl_id OR T2.cl_id is null)
    AND (T2.fornecedor_id = P.fornecedor_id OR T2.fornecedor_id = '')
  INNER JOIN sqldados.stk AS S
    ON  codigo = prdno
    AND S.grade  = P.grade
    AND S.storeno = T2.storeno
WHERE foralinha = 0
  AND usoconsumo = 0
HAVING quant <> 0;

INSERT INTO estoque(quant, inventario_id, produto_id)
SELECT quant, inventario_id, produto_id
FROM T3
