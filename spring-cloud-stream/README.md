```bash
skaffold build \
  --profile="vm-microk8s-springboot2" \
  --skip-tests=true \
  --default-repo="localhost:32000"
```

```bash  
skaffold dev \
  --profile="vm-microk8s-springboot2" \
  --skip-tests=true \
  --default-repo="localhost:32000" \
  --tail=false \
  --port-forward=user
```

```bash
skaffold build \
  --profile="vm-microk8s-springboot3" \
  --skip-tests=true \
  --default-repo="localhost:32000"
```

```bash  
skaffold dev \
  --profile="vm-microk8s-springboot3" \
  --skip-tests=true \
  --default-repo="localhost:32000" \
  --tail=false \
  --port-forward=user
```

```bash
watch -n 1 "curl -s 'http://localhost:8080/api/topn?topn=20' | jq -r '.[]|[.counter,.crtTimestamp]|@tsv'"
```