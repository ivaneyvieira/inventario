#!/bin/bash

function tamanho() {
  FILE=$1
  DIM=$2
  mkdir $DIM 2> /dev/null
  convert -resize ${DIM}x${DIM} ${FILE} ${DIM}/${FILE}
}

function tamanhos() {
  tamanho $1 128
  tamanho $1 90
  tamanho $1 64
  tamanho $1 50
  tamanho $1 40
  tamanho $1 32
  tamanho $1 21 
  tamanho $1 16
}

for ARQ in *.png
do
  tamanhos $ARQ
done 