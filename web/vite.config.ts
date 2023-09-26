import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'
import viteCompression from 'vite-plugin-compression'
import * as path from 'path'
import {PreRenderedAsset} from "rollup";
// import {ComponentResolveResult} from "unplugin-vue-components/types";

// https://vitejs.dev/config/
export default defineConfig({
    server: {
        port: 2222,
        open: true,
        proxy: {
            '/api': {
                target: 'http://127.0.0.9:2233/api',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '') // 不可以省略rewrite
            }
        }
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src/'),
            'Global': path.resolve(__dirname, './src/Global.ts'),
            '@components': path.resolve(__dirname, './src/components'),
            '@pages': path.resolve(__dirname, './src/pages'),
            '@icons': path.resolve(__dirname, './src/icons'),
            '@types': path.resolve(__dirname, './src/types'),
        }
    },
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver({
                importStyle: false
            })],
        }),
        Components({
            // exclude:[/.*\.vue/i],
            types: [
                {
                    from: '@components',
                    names: ['CommonHeader', 'AynuCard']
                }
            ],
            resolvers: [
                ElementPlusResolver({
                    importStyle: false
                }),
            ],
        }),
        viteCompression({
            verbose: true,
            algorithm: 'gzip',
            ext: '.gz',
            filter: (file) => {
                return !/\.(png|jpg|jpeg|zip)$/i.test(file)
            },
            threshold: 1024,
            compressionOptions: {
                level: 9
            }
        })
    ],
    build: {
        minify: "terser",
        cssMinify: "lightningcss",
        reportCompressedSize: false,
        rollupOptions: {
            output: {
                assetFileNames: (chunkInfo: PreRenderedAsset) => {
                    if (/.*\.(png|jpg|webp|jpeg|svg)$/i.test(chunkInfo.name))
                        return `img/[ext]/[name]-[hash].[ext]`
                    else if (/.*\.css$/i.test(chunkInfo.name))
                        return `css/[name]-[hash].[ext]`
                    else
                        return `assets/[name]-[hash].[ext]`
                },
                entryFileNames: `js/[name]-[hash].js`,
                chunkFileNames: `js/[name]-[hash].js`
            }
        }
    },

})
