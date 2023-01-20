```bash
skaffold build \
  --profile="vm-microk8s-sb3" \
  --skip-tests=true \
  --default-repo="localhost:32000"
```

```bash  
skaffold dev \
  --profile="mac-microk8s-sb2" \
  --skip-tests=true \
  --tail=false \
  --port-forward=user
```

```bash  
skaffold dev \
  --profile="mac-microk8s-sb3" \
  --skip-tests=true \
  --tail=false \
  --port-forward=user
```

```bash  
skaffold dev \
  --profile="vm-microk8s-sb2" \
  --skip-tests=true \
  --default-repo="localhost:32000" \
  --tail=false \
  --port-forward=user
```

```bash  
skaffold dev \
  --profile="vm-microk8s-sb3" \
  --skip-tests=true \
  --default-repo="localhost:32000" \
  --tail=false \
  --port-forward=user
```

```bash
watch -n 1 "curl -s 'http://localhost:8080/api/topn?topn=20' | jq -r '.[]|[.counter,.crtTimestamp]|@tsv'"
```