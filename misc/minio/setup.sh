#!/bin/sh

# Create a Minio bucket named "chum"
mc mb myminio/chum

#apply the access policy to policy.json for the bucket
mc policy add myminio/chum policy.json


