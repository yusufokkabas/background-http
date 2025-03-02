export interface backgroundHttpPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
